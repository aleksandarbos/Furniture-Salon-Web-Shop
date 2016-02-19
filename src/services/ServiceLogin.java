package services;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Account;
import beans.Accounts;
import beans.Reports;
import beans.Salons;
import beans.ShoppingCart;


@Path("/login")
public class ServiceLogin {

	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest req;
	@Context
	HttpServletResponse resp;
	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "caoo";
	}
	
	@POST
	@Path("/checkLogin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String checkLogin(Account account) throws IOException, URISyntaxException {
						
		String username = account.getUsername();
		String pass = account.getPassword();
		
		for(Account ac: getAccountsInstance().getAccounts()) {
			if(username != null && username.equals(ac.getUsername())) {
			   if(pass != null && pass.equals(ac.getPassword())) {
				   Salons salons = new Salons(ctx.getRealPath("")+"\\data\\salons.dat");
				   req.getSession().setAttribute("salons", salons); 			// dodajem salone na nivo aplikacije, posto je uspesno ulogovan
				   req.getSession().setAttribute("user", ac);	// korisnik ide na nivo sesije
				   req.getSession().setAttribute("shoppingCart", new ShoppingCart());	// dodaj korpu
				   req.getSession().setAttribute("reports", new Reports(ctx.getRealPath("")+"\\data\\reports.dat"));
				return "1";		// sve je ok
			   }
			return "2";			// postoji username, ali sifra ne valja
			}
		}
		return "3";		// ne postoji nalog uopste...
	}
	
	public Accounts getAccountsInstance() {
		Accounts accounts = (Accounts) ctx.getAttribute("accounts");
		if(accounts == null) {
			accounts = new Accounts(ctx.getRealPath("")+"\\data\\accounts.dat");
			//ctx.setAttribute("accounts", accounts);   ???? 
		}
		return accounts;
	}
	
	@GET
	@Path("/logOut")
	public void logOut() {
		req.getSession().invalidate();
	}
	
	@GET
	@Path("/getSessionUserName")
	@Produces(MediaType.TEXT_PLAIN)
	public String getSessionUserName() {
		Account account = (Account) req.getSession().getAttribute("user");
		return account.getRole();
	}
}
