<?php
function sanitize_input($data){
    return htmlspecialchars(stripslashes(trim($data)));
}


if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Check if POST variables are set and not empty
    $required_fields = ['name', 'latitude', 'longitude', 'rating', 'genre', 'description', 'domicile'];
    $is_valid = true;
    foreach ($required_fields as $field) {
        if (empty($_POST[$field])) {
            $is_valid = false;
            break;
        }
    }

    if ($is_valid) {
        $name = sanitize_input($_POST["name"]);
        $latitude = sanitize_input($_POST["latitude"]);
        $longitude = sanitize_input($_POST["longitude"]);
        $rating = sanitize_input($_POST["rating"]);
        $genre = sanitize_input($_POST["genre"]);
        $description = sanitize_input($_POST["description"]);

        $homepage = !empty($_POST["homepage"]) ? sanitize_input($_POST["homepage"]) : null;
        $sns = !empty($_POST["sns"]) ? sanitize_input($_POST["sns"]) : null;

        $domicile = sanitize_input($_POST["domicile"]);

        // Validate and sanitize other inputs...

        // Load existing data
        $data = json_decode(file_get_contents("shops.json"), true);

        // Check if data is an array
        if (!is_array($data)) {
            $data = [];
        }

        // Add new shop data
        $new_shop = array(
            'id' => count($data),
            'name' => $name,
            'location' => array('latitude' => $latitude, 'longitude' => $longitude),
            'rating' => $rating,
            'genre' => $genre,
            'info' => array('description' => $description, 'homepage' => $homepage, 'sns' => $sns, 'domicile' => $domicile)
        );
        $data[] = $new_shop;

        // Save back to file
        file_put_contents("shops.json", json_encode($data, JSON_PRETTY_PRINT));
    }
}

?>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/add_shop.css">
    <title>店を追加しました</title>
</head>
<body>
<?php if ($is_valid): ?>
    <h1>店を追加しました！</h1>
<?php else: ?>
    <h1>エラー：フォームが正しく記入されていません。</h1>
<?php endif; ?>
<a href="add_shop.php">別の店を追加</a>
</body>
</html>

