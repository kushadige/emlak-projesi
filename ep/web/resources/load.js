const toggler = document.querySelectorAll('.toggler');

toggler.forEach((el) => {
    el.addEventListener('click', (e) => {
        const heart = e.target.nextElementSibling;
        if(heart.classList.contains('red-text')){
            heart.classList.remove('red-text');
        } else {
            heart.classList.add('red-text');
        }
    });
});


window.onscroll = () => {
    hideNav();
};

function hideNav(){
    if(window.scrollY > window.innerHeight){
        document.querySelector('.navbar').style.display = 'none';
    } else {
        document.querySelector('.navbar').style.display = 'flex';
    }
}