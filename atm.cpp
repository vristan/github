//Develop ATM Management System with following modules in the implementation
//• Check Total Balance
//• Deposit Money
//• Withdraw Money
//• Reset Pin

//Made by Aditya Paliwal and Aditya Kumar
//Section : 04
//Roll no: 22SCSE1010615 & 22SCSE1010707


#include<iostream>
#include<string>
#include<iomanip>
#include<vector>
using namespace std;

class Bank {
private :

    std::string name;
    double balance;
    int PIN;
    std::string security_code;
    std::string surname;


public :

    long long account_number;
    void make_new_account(std::string acc_name,string sr_name, long long account_num,int Pin, double bal_amt,std::string Sec_code);
    void deposit_money(double amt,long long acc_num);
    bool check_pin();
    void withdraw_money(double amt);
    void set_balance(double amt);
    double get_balance();
    void show_balance() ;
    void reset_pin(int pin);
    void set_details(std::string n, std:: string s,long long acc_num,int pin, double bal);
    bool checkAccountNum(long long acc_num) const;

};


static Bank *account= new Bank[5];

void Bank::make_new_account(std::string acc_name,std::string sr_name, long long account_num,int Pin, double bal_amt,std::string Sec_code)
{
    name=acc_name;
    surname=sr_name;
    account_number=account_num;
    PIN=Pin;
    balance=bal_amt;
    security_code=Sec_code;

}
bool Bank::checkAccountNum(long long acc_num) const{

    if(account_number==acc_num)
    {
        return true;
    }
    else
        return false;
}

void Bank::set_balance (double amt)
{
    balance=amt;
}
double Bank::get_balance() {
    return balance;
}
void Bank::reset_pin(int pin){
    cout<<"Seems like you have forgot your password !\n";
    cout<<"No worries ! Just answer one security question \n";
    cout<<"Enter your favourite cousin name : ";
    string security_code_answer;
    cin>>security_code_answer;
    if(security_code_answer==security_code)
    {
        PIN=pin;
        cout<<endl<<"Account Holder Name :  "<<name<<" "<<surname<<endl<<"Account number :  "<<account_number<<endl<<"Balance : "<<balance<<endl;

        cout<<"Password changed successfully !\n";

    }
    else
    {
        cout<<"Wrong answer ! Sorry password can't be changed .\n";
    }


}
void Bank::withdraw_money(double amt) {
    if(check_pin()) {
        if (balance >= amt) {
            balance -= amt;
            cout << endl << amt << " withdrawn  " << endl;
        } else {
            cout << " Insufficient Balance" << endl;
        }
    }
}

void Bank::deposit_money(double amt,long long acc_num) {
    if (acc_num==account_number){
        balance+=amt;
        cout<<endl<<amt<<" deposited !! \n";
        cout<<endl<<"Account Holder Name :  "<<name<<" "<<surname<<endl<<"Account number :  "<<account_number<<endl<<"Current Balance : "<<balance<<endl;

    }
    else
    {
        cout<<"\nAccount details not found !\n ";
    }

}
void Bank::set_details(std::string n,std::string s,long long acc_num, int pin, double bal) {
    name = n;
    surname=s;
    account_number = acc_num;
    balance = bal;
    PIN = pin;
}

void Bank::show_balance()  {
    if(check_pin()){

        cout<<endl<<"Account Holder Name :  "<<name<<" "<<surname<<endl<<"Account number :  "<<account_number<<endl<<"Balance : "<<balance<<endl;
    }
}

bool Bank::check_pin() {
    int pin;
    cout<<"\nEnter PIN : ";
    cin>>pin;
    if (pin==PIN){
        return true;
    } else{
        cout<<"\nIncorrect pin...\n ";
        return false;
    }
}

void makeNewAccount()
{

    static int counter = 0;
    cout<<"Enter the first name for the new account : ";
    char acc_name[30];
    cin>>acc_name;
    cout<<"Enter the last name for the new account  : ";
    char sr_name[30];
    cin>>sr_name;
    cout<<"Enter Account number : ";
    long long acc_num;
    cin>>acc_num;
    cout<<"Enter PIN : ";
    int pin;
    cin>>pin;
    cout<<"Enter balance : ";
    double balance;
    cin>>balance;
    cout<<"(Security Question) This question will be asked when you forget your password and want to reset it in future \n"
          "Enter your favourite cousin name : ";
    string security_question_answer;
    cin>>security_question_answer;
    account[counter].make_new_account(acc_name,sr_name,acc_num,pin,balance,security_question_answer);
    cout<<endl<<"Account created !\n";
    counter++;
}

Bank *check_Account_Num(long long Account_num) {

    for(int i=0;i<5;i++)
    {
        if(account[i].checkAccountNum(Account_num))
        {
            return &account[i];
        }
    }

    cout<<"Account number not found !\n Kindly Re-enter : ";
    long long Acc_number;
    cin>>Acc_number;
    return check_Account_Num(Acc_number);

}



int main()
{
    cout<<"********** Welcome to IndianExpress ATM **********\n\n";
    makeNewAccount();
    bool loop=true;
    LOOP:
    while(loop)
    {

        cout<<"\n\n********** Welcome to IndianExpress ATM **********"
              "\n"
              "\n"
              "(1) Open existing account "
              "\n"
              "(2) Create new account "
              "\n"
              "(3) Quit \n";
        int start_menu_choice;
        cout<<endl<<"Enter option : ";
        cin>>start_menu_choice;

        if(start_menu_choice==1)
        {

            cout<<"Enter your Account number : ";
            long long account_num;
            cin>>account_num;
            Bank *Current_User_ID=check_Account_Num(account_num);
            bool loop_2=true;
            while(loop_2)
            {
                cout<<"---------------------------------------------------------"
                      "\n|\t************ Welcome to IndianExpress ATM *********\n|";
                cout<<"\n|\tSelect options to choose :- \n|"
                      "\n|\t(1) Check Balance "
                      "\n|\t(2) Withdraw Money "
                      "\n|\t(3) Deposit Money "
                      "\n|\t(4) Reset PIN"
                      "\n|\t(5) Exit "
                      "\n|\t(6) Go back "
                      "\n|"
                      "---------------------------------------------------------"
                      ""
                      "\n|\tEnter option : ";
                int op;
                cin>>op;
                cout<<"|---------------------------------------------------------\n";

                switch(op)
                {
                    case 1:
                    {
                        Current_User_ID->show_balance();


                    }break;
                    case 2:
                    {
                        RETRY:
                        cout<<"\nEnter the money you want to withdraw : ";
                        double money_to_be_withdraw;
                        cin>>money_to_be_withdraw;
                        if(money_to_be_withdraw<=0)
                        {
                            cout<<"transaction not possible ! Enter amount greater than 0 .\n";
                            goto RETRY;
                        }
                        Current_User_ID->withdraw_money(money_to_be_withdraw);
                    }break;
                    case 3:
                    {
                        cout<<"\nEnter money to be deposited : ";
                        double money_to_be_deposited;
                        cin>>money_to_be_deposited;
                        Current_User_ID->deposit_money(money_to_be_deposited,Current_User_ID->account_number);
                    }break;
                    case 4:
                    {
                        cout<<"\nEnter new PIN : ";
                        int new_pin;
                        cin>>new_pin;
                        Current_User_ID->reset_pin(new_pin);
                    }break;
                    case 5:
                    {
                        cout<<"\n\n Exiting .........  \n\n";
                        return 0;
                    }break;
                    case 6:
                    {
                        cout<<"Going back !\n";
                        goto LOOP;

                    }break;
                    default:
                    {
                        cout<<"Invalid Choice ----------- !\n";
                    }



                }
            }
        }
        else if (start_menu_choice==2)
        {


            makeNewAccount();
        }
        else if(start_menu_choice==3)
        {
            loop=false;
        }
        else
        {
            cout<<endl<<"Invalid option !\n";
        }

    }
    delete[] account;
}