package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO ;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }
        
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account register(Account acc){
        Account account = accountDAO.getAccountByUserName(acc.getUsername());
        if(account!=null || acc.getUsername() == null || acc.getPassword().length()<4 || acc.getUsername().isBlank()){
            return null;
        }
        return accountDAO.register(acc);
    }

    public Account login(String un, String pwd){
        Account account = accountDAO.getAccountByUserName(un);
        if(account == null||!account.getPassword().equals(pwd)){
            return null;
        }
        return account;
    }
    
}
