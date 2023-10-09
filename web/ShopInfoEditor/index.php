<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">
    <title>Shops List</title>
    <script src="js/idChange.js"></script>
</head>
<body>
<h1 id="shop-header">Shop ID: 0</h1>
<div id="shopTableContainer">
    <?php
    $data = json_decode(file_get_contents("shops.json"), true);
    foreach ($data as $index => $shop) {
        echo "<table class='shopTable' id='shop{$index}' style='display: none;'>";
        echo "<tr><th>ID</th><td>{$shop['id']}</td></tr>";
        echo "<tr><th>店名</th><td>{$shop['name']}</td></tr>";
        echo "<tr><th>緯度</th><td>{$shop['location']['latitude']}</td></tr>";
        echo "<tr><th>経度</th><td>{$shop['location']['longitude']}</td></tr>";
        echo "<tr><th>評価</th><td>{$shop['rating']}</td></tr>";
        echo "<tr><th>ジャンル</th><td>{$shop['genre']}</td></tr>";
        echo "<tr><th>説明</th><td>{$shop['info']['description']}</td></tr>";

        echo "<tr><th>ホームページ</th><td>";
        echo "<a href='https://{$shop['info']['homepage']}'>";
        echo ($shop['info']['homepage'] ?? "null") . "</a></td></tr>";

        echo "<tr><th>SNS</th><td><a href='https://{$shop['info']['sns']}'>";
        echo ($shop['info']['sns'] ?? "null") . "</a></td></tr>";
        echo "<tr><th>住所</th><td>{$shop['info']['domicile']}</td></tr>";

        // Add Map View button
        $latitude = $shop['location']['latitude'];
        $longitude = $shop['location']['longitude'];
        echo "<tr><td colspan='2'>";
        echo "<a href='https://www.google.com/maps?q={$latitude},{$longitude}' target='_blank'>";
        echo "<button class='b orange'>Map View</button>";
        echo "</a></td></tr>";

        // Add Delete button at the bottom right of the table
        echo "<tr><td colspan='2' style='text-align: right;'>";
        echo "<form action='delete_shop.php' method='post'>";
        echo "<input type='hidden' name='id' value='{$shop['id']}'>";
        echo "<button type='submit' class='b red'>Delete</button>";
        echo "</form></td></tr>";


        echo "</table>";
    }
    ?>
</div>

<button onclick="prevShop()" class = "b control">Prev</button>
<a href="add_shop.php" style="text-decoration: none;">
    <button class="b add">Edit</button>
</a>
<button onclick="nextShop()" class = "b control">Next</button>

<script>
    let currentShopIndex = 0;
    const totalShops = <?php echo count($data); ?>;
    const shopId = document.getElementById('shop-id');


    function showShop(index) {
        document.getElementById('shop' + currentShopIndex).style.display = 'none';
        document.getElementById('shop' + index).style.display = 'table';
        currentShopIndex = index;
    }
    function nextShop() {
        const newIndex = (currentShopIndex + 1) % totalShops;
        changeHeader(+1);
        showShop(newIndex);
    }

    function prevShop() {
        const newIndex = (currentShopIndex - 1 + totalShops) % totalShops;
        changeHeader(-1);
        showShop(newIndex);
    }

    // Initially show the first shop
    showShop(0);
</script>
</body>
</html>
