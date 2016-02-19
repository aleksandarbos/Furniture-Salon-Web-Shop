package beans;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class Accounts implements Serializable{

	private ArrayList<Account> accounts = new ArrayList<Account>();
	
	public Accounts() {
		//this("accounts.txt");
	}
	
	public Accounts(String filePath) {
		
		readFile(filePath);
		
		System.out.println("loading data from: " + filePath);
		
	}

	private void readFile(String accountsFilePath) {
		// TODO Auto-generated method stub
		try {
			FileInputStream fileIn = new FileInputStream(accountsFilePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Accounts accs = (Accounts) in.readObject();
			this.accounts = accs.getAccounts();
			
			in.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}
	
	
	
}
