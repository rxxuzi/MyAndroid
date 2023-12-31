<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/add_shop.css">
    <title>新しい店を追加</title>
</head>
<body>
<h1>新しい店を追加</h1>
<form action="submit_shop.php" method="post">
    <label for="name">店名:</label>
    <input type="text" id="name" name="name" required><br>

    <label for="latitude">緯度:</label>
    <input type="number" id="latitude" name="latitude" step="any" required><br>

    <label for="longitude">経度:</label>
    <input type="number" id="longitude" name="longitude" step="any" required><br>

    <label for="rating">評価:</label>
    <input type="number" id="rating" name="rating" min="0" max="5" step="0.1" required><br>

    <label for="genre">ジャンル:</label>
    <input type="text" id="genre" name="genre" required><br>

    <label for="description">説明:</label>
    <textarea id="description" name="description" rows="4" required></textarea><br>

    <label for="homepage">ホームページ:</label>
    <input type="url" id="homepage" name="homepage"><br>

    <label for="sns">SNS:</label>
    <input type="url" id="sns" name="sns"><br>

    <label for="domicile">住所:</label>
    <input type="text" id="domicile" name="domicile" required><br>

    <input type="submit" value="店を追加">
</form>

<a href="index.php" style="text-decoration: none;">
    <button id="back-button" class="back">Back</button>
</a>
</body>
</html>
