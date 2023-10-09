<?php
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST["id"])) {
    $id_to_delete = $_POST["id"];

    // Load existing data
    $data = json_decode(file_get_contents("shops.json"), true);

    // Find and remove shop with the given ID
    foreach ($data as $index => $shop) {
        if ($shop['id'] == $id_to_delete) {
            array_splice($data, $index, 1);
            break;
        }
    }

    // Re-index IDs
    foreach ($data as $index => $shop) {
        $data[$index]['id'] = $index;
    }

    // Save back to file
    file_put_contents("shops.json", json_encode($data, JSON_PRETTY_PRINT));
}

// Redirect back to add_shop.php
header("Location: add_shop.php");
exit;

