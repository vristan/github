#include <iostream>
#include <fstream>
#include <vector>
#include <string>

using namespace std;

// Contact structure to store contact details
struct Contact {
    string name;
    string phone;
    string email;
};

// ContactManager class to manage contacts using file handling
class ContactManager {
private:
    const string filename = "C:\\Users\\Lenovo\\Desktop\\github\\contact management\\db\\contacts.txt"; // File to store contacts

public:
    // Function to add a new contact to file
    void addContact(const Contact& contact) {
        ofstream file(filename, ios::app);
        if (file.is_open()) {
            file << contact.name << "," << contact.phone << "," << contact.email << endl;
            cout << "Contact added successfully." << endl;
            file.close();
        } else {
            cerr << "Error opening file." << endl;
        }
    }

    // Function to display all contacts from file
    void displayContacts() {
        ifstream file(filename);
        if (file.is_open()) {
            cout << "Contacts:" << endl;
            string line;
            while (getline(file, line)) {
                cout << line << endl;
            }
            file.close();
        } else {
            cerr << "Error opening file." << endl;
        }
    }

    // Function to search for a contact by name in file
    void searchContact(const string& name) {
        ifstream file(filename);
        if (file.is_open()) {
            bool found = false;
            string line;
            while (getline(file, line)) {
                size_t pos = line.find(",");
                string contactName = line.substr(0, pos);
                if (contactName == name) {
                    cout << "Contact found:" << endl;
                    cout << line << endl;
                    found = true;
                    break;
                }
            }
            if (!found) {
                cout << "Contact not found." << endl;
            }
            file.close();
        } else {
            cerr << "Error opening file." << endl;
        }
    }

    // Function to update a contact by name in file
    void updateContact(const string& name, const Contact& newContact) {
        ifstream fileIn(filename);
        ofstream fileOut("temp.txt", ios::out);
        if (fileIn.is_open() && fileOut.is_open()) {
            bool updated = false;
            string line;
            while (getline(fileIn, line)) {
                size_t pos = line.find(",");
                string contactName = line.substr(0, pos);
                if (contactName == name) {
                    fileOut << newContact.name << "," << newContact.phone << "," << newContact.email << endl;
                    updated = true;
                } else {
                    fileOut << line << endl;
                }
            }
            fileIn.close();
            fileOut.close();
            remove(filename.c_str());
            rename("temp.txt", filename.c_str());

            if (updated) {
                cout << "Contact updated successfully." << endl;
            } else {
                cout << "Contact not found. Update failed." << endl;
            }
        } else {
            cerr << "Error opening file." << endl;
        }
    }

    // Function to delete a contact by name from file
    void deleteContact(const string& name) {
        ifstream fileIn(filename);
        ofstream fileOut("temp.txt", ios::out);
        if (fileIn.is_open() && fileOut.is_open()) {
            bool deleted = false;
            string line;
            while (getline(fileIn, line)) {
                size_t pos = line.find(",");
                string contactName = line.substr(0, pos);
                if (contactName == name) {
                    deleted = true;
                } else {
                    fileOut << line << endl;
                }
            }
            fileIn.close();
            fileOut.close();
            remove(filename.c_str());
            rename("temp.txt", filename.c_str());

            if (deleted) {
                cout << "Contact deleted successfully." << endl;
            } else {
                cout << "Contact not found. Deletion failed." << endl;
            }
        } else {
            cerr << "Error opening file." << endl;
        }
    }
};

// Function to display menu and handle user input
void displayMenu(ContactManager& manager) {
    cout << "Welcome to Contact Management System" << endl;
    cout << "1. Add Contact" << endl;
    cout << "2. Display Contacts" << endl;
    cout << "3. Search Contact" << endl;
    cout << "4. Update Contact" << endl;
    cout << "5. Delete Contact" << endl;
    cout << "6. Exit" << endl;
    cout << "Enter your choice: ";

    int choice;
    cin >> choice;
    cin.ignore(); // Clear the input buffer

    switch (choice) {
        case 1: {
            Contact newContact;
            cout << "Enter name: ";
            getline(cin, newContact.name);
            cout << "Enter phone: ";
            getline(cin, newContact.phone);
            cout << "Enter email: ";
            getline(cin, newContact.email);
            manager.addContact(newContact);
            break;
        }
        case 2:
            manager.displayContacts();
            break;
        case 3: {
            string name;
            cout << "Enter name to search: ";
            getline(cin, name);
            manager.searchContact(name);
            break;
        }
        case 4: {
            string name;
            cout << "Enter name to update: ";
            getline(cin, name);
            Contact newContact;
            cout << "Enter new name: ";
            getline(cin, newContact.name);
            cout << "Enter new phone: ";
            getline(cin, newContact.phone);
            cout << "Enter new email: ";
            getline(cin, newContact.email);
            manager.updateContact(name, newContact);
            break;
        }
        case 5: {
            string name;
            cout << "Enter name to delete: ";
            getline(cin, name);
            manager.deleteContact(name);
            break;
        }
        case 6:
            cout << "Exiting program." << endl;
            return;
        default:
            cout << "Invalid choice. Please enter a valid option." << endl;
    }

    displayMenu(manager); // Recursive call to display menu again
}

int main() {
    ContactManager manager;

    // Display menu for user interaction
    displayMenu(manager);

    return 0;
}
