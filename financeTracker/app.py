from tkinter import *
from tkinter import messagebox
from database import Database
from datetime import datetime

# Initialize database
db = Database()

# Function to add a new transaction
def add_transaction():
    title = title_entry.get()
    amount = amount_entry.get()
    category = category_entry.get()
    date = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    if title and amount and category:
        try:
            amount = float(amount)
            db.insert_transaction(title, amount, category, date)
            messagebox.showinfo("Success", "Transaction added successfully!")
            clear_fields()
        except ValueError:
            messagebox.showerror("Error", "Amount must be a number.")
    else:
        messagebox.showerror("Error", "All fields are required.")

# Function to clear input fields
def clear_fields():
    title_entry.delete(0, END)
    amount_entry.delete(0, END)
    category_entry.delete(0, END)

# Function to show all transactions
def show_transactions():
    transactions = db.get_all_transactions()
    if transactions:
        for transaction in transactions:
            print(transaction)
    else:
        print("No transactions found.")

# GUI setup
root = Tk()
root.title("Finance Management System")

title_label = Label(root, text="Title:")
title_label.grid(row=0, column=0, padx=10, pady=5)
title_entry = Entry(root)
title_entry.grid(row=0, column=1, padx=10, pady=5)

amount_label = Label(root, text="Amount:")
amount_label.grid(row=1, column=0, padx=10, pady=5)
amount_entry = Entry(root)
amount_entry.grid(row=1, column=1, padx=10, pady=5)

category_label = Label(root, text="Category:")
category_label.grid(row=2, column=0, padx=10, pady=5)
category_entry = Entry(root)
category_entry.grid(row=2, column=1, padx=10, pady=5)

add_button = Button(root, text="Add Transaction", command=add_transaction)
add_button.grid(row=3, column=0, columnspan=2, pady=10)

show_button = Button(root, text="Show Transactions", command=show_transactions)
show_button.grid(row=4, column=0, columnspan=2, pady=10)

root.mainloop()

# Close database connection when GUI is closed
db.close_connection()
