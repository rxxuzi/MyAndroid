let currentShopId = 0;

function updateHeaderWithShopId(shopId) {
    document.getElementById('shop-header').innerText = "Shop ID: " + shopId;
}

function changeHeader(direction) {
    // Assuming you have a variable 'totalShops' that holds the total number of shops.
    const totalShops = document.querySelectorAll('.shopTable').length;

    currentShopId += direction;
    if (currentShopId < 0) {
        currentShopId = totalShops - 1; // Go to the last shop if at the beginning
    } else if (currentShopId >= totalShops) {
        currentShopId = 0; // Go to the first shop if at the end
    }

    updateHeaderWithShopId(currentShopId);
    updateVisibleShop(currentShopId); // Assuming you have a function to update the visible shop details.
}

function updateVisibleShop(shopId) {
    // Hide all shop tables
    document.querySelectorAll('.shopTable').forEach(function(table) {
        table.style.display = 'none';
    });

    // Show the selected shop table
    document.getElementById('shop' + shopId).style.display = 'block';
}

window.onload = function() {
    updateHeaderWithShopId(currentShopId);
};