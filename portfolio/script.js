// Smooth scrolling for navigation links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function(e) {
        e.preventDefault();

        const target = document.querySelector(this.getAttribute('href'));
        target.scrollIntoView({
            behavior: 'smooth'
        });
    });
});

// Project Details Modal
var modal = document.getElementById('projectModal');
var btnDetails = document.querySelectorAll('.btn-details');
var span = document.getElementsByClassName('close')[0];

btnDetails.forEach(function(btn) {
    btn.addEventListener('click', function() {
        modal.style.display = 'block';
    });
});

span.addEventListener('click', function() {
    modal.style.display = 'none';
});

window.addEventListener('click', function(event) {
    if (event.target == modal) {
        modal.style.display = 'none';
    }
});
