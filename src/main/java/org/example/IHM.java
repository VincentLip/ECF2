package org.example;

import org.example.entities.Activity;
import org.example.entities.Address;
import org.example.entities.Customer;
import org.example.services.ActivityService;
import org.example.services.AdressService;
import org.example.services.CustomerService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class IHM {

    private CustomerService customerService;
    private AdressService adressService;
    private ActivityService activityService;

    private Scanner scanner;

    public IHM() {
        activityService = new ActivityService();
        customerService = new CustomerService();
        adressService = new AdressService();
        scanner = new Scanner(System.in);
    }

    public  void start() {
        String choice;
        do {
            menu();
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addCustomer();
                    break;
                case "2":
                    updateCustomer();
                    break;
                case "3":
                    deleteCustomer();
                    break;
                case "4":
                    displayAllCustomer();
                    break;
                case "5":
                    addAddress();
                    break;
                case "6":
                    updateAddress();
                    break;
                case "7":
                    displayAllAddress();
                    break;
                case "8":
                    addActivity();
                    break;
                case "9":
                    updateActivity();
                    break;
                case "10":
                    deleteActivity();
                    break;
                case "11":
                    displayActivity();
                    break;
                case "12":
                    addActivityToCustomer();
                    break;
                case "13":
                    deleteActivityToCustomer();
                    break;
                case "14":
                    getTotalDuration();
                    break;
                case "15":
                    getTotalPriceByCustomer();
                    break;
                case "16":
                    getTotalPrice();
                    break;


            }
        }while(!choice.equals("0"));
        customerService.end();
    }

    private void menu() {
        System.out.println("########  Menu  #########");
        System.out.println("***************** Menu Client **********************");
        System.out.println("1 -- Ajouter un client");
        System.out.println("2 -- Modifier les informations d'un client");
        System.out.println("3 -- Supprimer les informations d'un client");
        System.out.println("4 -- Afficher les informations d'un client");
        System.out.println();
        System.out.println("***************** Menu Adresse **********************");
        System.out.println("5 -- Ajouter une adresse à un client");
        System.out.println("6 -- Modifier les informations d'une adresse");
        System.out.println("7 -- Afficher les informations d'une adresse");
        System.out.println();
        System.out.println("***************** Menu Activité **********************");
        System.out.println("8 -- Ajouter une activité ");
        System.out.println("9 -- Modifier les informations d'une activité");
        System.out.println("10 -- Supprimer les informations d'une activité");
        System.out.println("11 -- Afficher les informations d'une activité");
        System.out.println();
        System.out.println("***************** Menu Réservation **********************");
        System.out.println("12 -- Ajouter une activité à un client");
        System.out.println("13 -- Supprimer les activités pour un client");
        System.out.println();
        System.out.println("***************** Menu Information **********************");
        System.out.println("14 -- Voir la durée totale des activitées pour un client");
        System.out.println("15 -- Voir le montant total des activitées pour un client");
        System.out.println("16 -- Voir le montant total des activitées du site");
        System.out.println("0 -- Quitter ");

    }

    private void addCustomer(){
        System.out.println("Merci de saisir le nom du client : ");
        String lastname = scanner.nextLine();
        System.out.println("Merci de saisir le prenom du client : ");
        String firstname = scanner.nextLine();
        System.out.println("Merci de saisir la date de naissance (dd/MM/yyyy) : ");
        String birthday = scanner.nextLine();
        System.out.println("Merci de saisir l'id de l'adresse : ");

        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthday);
            customerService.create(new Customer(lastname,firstname,date));
            System.out.println("Client ajouté");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private void updateCustomer(){
        System.out.println("Merci de saisir l'id du client : ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Customer customer = customerService.findById(id);
        System.out.println("Merci de saisir le nom du client : ");
        String lastname = scanner.nextLine();
        customer.setLastName(lastname);
        System.out.println("Merci de saisir le prenom du client : ");
        String firstname = scanner.nextLine();
        customer.setFirstName(firstname);
        System.out.println("Merci de saisir la date de naissance (dd/MM/yyyy) : ");
        String birthday = scanner.nextLine();
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthday);
            customer.setBirthday(date);
            customerService.update(customer);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void deleteCustomer(){
        System.out.println("Merci de saisir l'id du client : ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Customer customer = customerService.findById(id);
        customerService.delete(id);
    }
    private void displayAllCustomer(){
        List<Customer> customers = customerService.findAll();
        for (Customer c: customers) {
            System.out.println(c);
        }
    }

    private void addAddress(){
        System.out.println("Merci de saisir l'id du client : ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Merci de saisir le numéro de rue : ");
        int nb = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Merci de saisir le nom de la rue : ");
        String street = scanner.nextLine();
        System.out.println("Merci de saisir le nom de la ville : ");
        String city = scanner.nextLine();
        System.out.println("Merci de saisir le code postal : ");
        String postalcode = scanner.nextLine();

        Address address = new Address(nb,street,city,postalcode);
        if(customerService.addAdress(address, id)) {
            System.out.println("Adresse ajoutée");
        }else {
            System.out.println("Erreur ajout de l'adresse");
        }
    }

    private void updateAddress(){
        System.out.println("Merci de saisir l'id de l'adresse : ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Address address = adressService.findById(id);

        System.out.println("Merci de saisir le numéro de rue : ");
        int nb = scanner.nextInt();
        scanner.nextLine();
        address.setNb(nb);
        System.out.println("Merci de saisir le nom de la rue : ");
        String street = scanner.nextLine();
        address.setStreet(street);
        System.out.println("Merci de saisir le nom de la ville : ");
        String city = scanner.nextLine();
        address.setCity(city);
        System.out.println("Merci de saisir le code postal : ");
        String postalcode = scanner.nextLine();
        address.setPostalCode(postalcode);
        adressService.update(address);
    }

    private void displayAllAddress(){
        List<Address> addresses = adressService.findAll();
        for (Address a: addresses) {
            System.out.println(a);
        }
    }

    private void addActivity(){
        System.out.println("Merci de saisir le nom de l'activité : ");
        String name = scanner.nextLine();
        System.out.println("Merci de saisir la durée de l'activité : ");
        int duration = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Merci de saisir le prix de l'activité : ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Activity activity = new Activity(name,duration,price);

        if(activityService.create(activity)) {
            System.out.println("Activité ajoutée");
        }else {
            System.out.println("Erreur ajout de l'adresse");
        }
    }

    private void updateActivity(){
        System.out.println("Merci de saisir l'id de l'activité : ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Activity activity = activityService.findById(id);

        System.out.println("Merci de saisir le nom de l'activité : ");
        String name = scanner.nextLine();
        activity.setName(name);
        System.out.println("Merci de saisir la durée de l'activité : ");
        int duration = scanner.nextInt();
        scanner.nextLine();
        activity.setDuration(duration);
        System.out.println("Merci de saisir le prix de l'activité : ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        activity.setPrice(price);
        activityService.update(activity);
    }

    private void deleteActivity(){
        System.out.println("Merci de saisir l'id de l'activité : ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Activity activity = activityService.findById(id);
        activityService.delete(id);
    }

    private void displayActivity(){
        List<Activity> activities = activityService.findAll();
        for (Activity a: activities) {
            System.out.println(a);
        }
    }

    private void addActivityToCustomer(){
        System.out.println("Merci de saisir l'id de l'activité : ");
        Long idActivity = scanner.nextLong();
        scanner.nextLine();
        Activity activity = activityService.findById(idActivity);

        System.out.println("Merci de saisir l'id du client : ");
        Long idCustomer = scanner.nextLong();
        scanner.nextLine();

        customerService.addActivity(activity,idCustomer);

    }

    private void deleteActivityToCustomer(){
        System.out.println("Merci de saisir l'id du client : ");
        Long idCustomer = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Merci de saisir l'id de l'activité : ");
        Long idActivity = scanner.nextLong();
        scanner.nextLine();
        try {
            activityService.deleteCustomerActivity(idCustomer,idActivity);
            System.out.println("Suppression Ok");
        }
        catch(Exception ex) {
            System.out.println("erreur suppression");
        }
    }

    private void getTotalDuration(){
        System.out.println("Merci de saisir l'id du client : ");
        Long idCustomer = scanner.nextLong();
        scanner.nextLine();
        List totalDuration = customerService.filterDurationTotal(idCustomer);
        for (Object o: totalDuration) {
            Object[] p = (Object[]) o;
            System.out.println("Pour le client " + p[0] + " "+p[1]+" la durée totale de ces activitées est de "+p[2] +" Heures");
        }
    }

    private void getTotalPriceByCustomer(){
        System.out.println("Merci de saisir l'id du client : ");
        Long idCustomer = scanner.nextLong();
        scanner.nextLine();
        List totalDuration = customerService.filterTotalPriceByCustomer(idCustomer);
        for (Object o: totalDuration) {
            Object[] p = (Object[]) o;
            System.out.println("Pour le client " + p[0] + " "+p[1]+" la durée totale de ces activitées est de "+p[2] +" euros");
        }
    }

    private void getTotalPrice(){
        double totalDuration = customerService.filterTotalPrice();
        System.out.println("Le montant total pour le site est de : " + totalDuration + "euros");
    }
}
