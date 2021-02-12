var notificationBox = document.getElementById('box');
var toggle = false;

function toggleBox(){
    if(toggle){
        notificationBox.style.height = '0px';
        notificationBox.style.opacity ='0%';
        toggle = false;
    }
    else{
        notificationBox.style.height = 'auto';
        notificationBox.style.opacity= '100%';
        toggle = true;
    }
}

function deleteItem(reference) {
    var parent = reference.parentElement;
    reference.remove();
    parent.remove();

}