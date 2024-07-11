import sqlite3

class Database:
    def __init__(self, db_name='finance.db'):
        self.conn = sqlite3.connect(db_name)
        self.cur = self.conn.cursor()
        self.create_tables()

    def create_tables(self):
        self.cur.execute('''
            CREATE TABLE IF NOT EXISTS transactions (
                id INTEGER PRIMARY KEY,
                title TEXT NOT NULL,
                amount REAL NOT NULL,
                category TEXT,
                date TEXT NOT NULL
            )
        ''')
        self.conn.commit()

    def insert_transaction(self, title, amount, category, date):
        self.cur.execute('''
            INSERT INTO transactions (title, amount, category, date)
            VALUES (?, ?, ?, ?)
        ''', (title, amount, category, date))
        self.conn.commit()

    def get_all_transactions(self):
        self.cur.execute('SELECT * FROM transactions')
        return self.cur.fetchall()

    def close_connection(self):
        self.conn.close()
