package store.controller;

import store.model.Cashier;
import store.model.Customer;
import store.model.Manager;
import store.model.Order;
import store.model.Products;
import store.model.Promotions;
import store.model.Storage;
import store.view.Input;
import store.view.Output;

public class Controller {
    private boolean isContinue;

    public void openStore() {
        prepareStore();
        do {
            Output.printStatus();
            Order order = checkOrder();

            Manager manager = checkManager(order);
            Cashier cashier = checkCashier(manager);

            Output.printReceipt(manager, cashier);
        } while (checkCustomer());
    }

    private void prepareStore() {
        Storage storage = new Storage();

        Products.restockProducts(storage);
        Promotions.recallPromotions(storage);
    }

    private Order checkOrder() {
        do {
            try {
                isContinue = false;
                String response = Input.askOrder();
                Order order = new Order(response);
                return order;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private Manager checkManager(Order order) {
        do {
            try {
                Manager manager = new Manager();
                manager.handleOrder(order);
                return manager;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private Cashier checkCashier(Manager manager) {
        do {
            try {
                String response = Input.askMemberDiscount();
                Cashier cashier = new Cashier();
                cashier.checkTotalMemberDiscount(response, manager);
                return cashier;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private boolean checkCustomer() {
        do {
            try {
                String response = Input.askLeave();
                Customer customer = new Customer(response);

                return checkIsContinue(customer);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private boolean checkIsContinue(Customer customer) {
        isContinue = customer.isResponseYes();
        if (isContinue) {
            System.out.println();
        }
        return isContinue;
    }
}