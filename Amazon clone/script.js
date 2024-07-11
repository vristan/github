document.addEventListener('DOMContentLoaded', function() {
    const productsContainer = document.getElementById('products');
    const searchInput = document.getElementById('search');
    const cartItemsContainer = document.getElementById('cart-items');
    const cartTotalContainer = document.getElementById('cart-total');
    const modal = document.getElementById('modal');
    const modalCloseBtn = document.getElementById('modal-close');

    // Sample product data (can be replaced with dynamic data from an API)
    let products = [
        { id: 1, name: 'Product 1', price: 49.99, image: 'product1.jpg', description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.' },
        { id: 2, name: 'Product 2', price: 29.99, image: 'product2.jpg', description: 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.' },
        { id: 3, name: 'Product 3', price: 19.99, image: 'product3.jpg', description: 'Nulla facilisi. Etiam auctor ornare odio, sed fringilla ligula vestibulum sit amet.' },
        { id: 4, name: 'Product 4', price: 39.99, image: 'product4.jpg', description: 'Donec bibendum elit sed sapien blandit, ac ultrices lacus fermentum.' },
        { id: 5, name: 'Product 5', price: 59.99, image: 'product5.jpg', description: 'Nam viverra, dui vel ultricies dignissim, neque ligula finibus turpis, ut rhoncus sapien nisi ut turpis.' },
    ];

    // Function to display products
    function displayProducts(products) {
        productsContainer.innerHTML = '';
        products.forEach(product => {
            const card = document.createElement('div');
            card.classList.add('card');
            card.innerHTML = `
                <img src="images/${product.image}" alt="${product.name}">
                <h2>${product.name}</h2>
                <p>$${product.price}</p>
                <button class="btn-details" onclick="openModal(${product.id})">View Details</button>
                <button class="btn-cart" onclick="addToCart(${product.id})">Add to Cart</button>
            `;
            productsContainer.appendChild(card);
        });
    }

    // Initial display of products
    displayProducts(products);

    // Search functionality
    searchInput.addEventListener('input', function() {
        const searchTerm = searchInput.value.trim().toLowerCase();
        const filteredProducts = products.filter(product =>
            product.name.toLowerCase().includes(searchTerm)
        );
        displayProducts(filteredProducts);
    });

    // Function to open modal with product details
    function openModal(productId) {
        const product = products.find(p => p.id === productId);
        if (product) {
            modal.innerHTML = `
                <div class="modal-content">
                    <span id="modal-close" class="modal-close">&times;</span>
                    <img src="images/${product.image}" alt="${product.name}">
                    <h2>${product.name}</h2>
                    <p>$${product.price}</p>
                    <p>${product.description}</p>
                    <button class="btn-cart" onclick="addToCart(${product.id})">Add to Cart</button>
                </div>
            `;
            modal.style.display = 'block';
        }
    }

    // Function to close modal
    modalCloseBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    // Function to simulate adding to cart
    function addToCart(productId) {
        const product = products.find(p => p.id === productId);
        if (product) {
            const cartItem = document.createElement('div');
            cartItem.classList.add('cart-item');
            cartItem.innerHTML = `
                <span>${product.name}</span>
                <span>$${product.price}</span>
            `;
            cartItemsContainer.appendChild(cartItem);

            updateCartTotal();
        }
    }

    // Function to update cart total
    function updateCartTotal() {
        let total = 0;
        const cartItems = cartItemsContainer.getElementsByClassName('cart-item');
        Array.from(cartItems).forEach(item => {
            const price = parseFloat(item.getElementsByTagName('span')[1].innerText.replace('$', ''));
            total += price;
        });
        cartTotalContainer.innerText = `Total: $${total.toFixed(2)}`;
    }

    // Close modal when clicking outside of it
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
});
