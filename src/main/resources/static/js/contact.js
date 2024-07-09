const viewcontactdata = document.getElementById("contact_model_show_data");


const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
    id: 'contact_model_show_data',
    override: true
};


const modal = new Modal(viewcontactdata, options, instanceOptions);


function openContactModal() {
    modal.show();
}

function closeContactModal() {
    modal.hide();
}

async function loadContactData(id) {


    console.log(id);

    try {

        const data = await (await fetch(`http://localhost:8080/api/contact/${id}`)).json();
        console.log(data);
        console.log(data.contactImage);

        document.querySelector("#contact_name").innerHTML = data.name;
        document.querySelector("#contact_email").innerHTML = data.email;
        document.querySelector("#contact_phoneNumber").innerHTML = data.phoneNumber;
        document.querySelector("#contact_contactImage").src = data.contactImage;
        document.querySelector("#contact_address").innerHTML = data.address;


        openContactModal();

    } catch (error) {
        console.log("Error: ", error);
    }


}


// const deleteContact = document.getElementById("delet_modal");
// const instanceOptions1 = {
//     id: 'delet_modal',
//     override: true
// };
// const modal1 = new Modal(deleteContact, options, instanceOptions1);
//
// function opendeleteContactModal() {
//     modal1.show();
// }
//
// function closedeleteContactModal() {
//     modal1.hide();
// }
//
// function deleteContactModal(id) {
//     console.log('contact id is : - ' + id);
//     const url = `http://localhost:8080/user/contacts/delete/${id}`;
// window.location.replace(url)
//
// }
//

async function deleteContactModal(id) {
    Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {
            console.log('contact id is : - ' + id);
            const url = "http://localhost:8080/user/contacts/delete/" + id;
            window.location.replace(url)
        }
    });
}





