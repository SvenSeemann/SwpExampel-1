package fviv;

import fviv.areaPlanner.AreaItem;
import fviv.areaPlanner.AreaItemsRepository;
import fviv.festival.Festival;
import fviv.festival.FestivalRepository;
import fviv.location.Location;
import fviv.location.LocationRepository;
import fviv.model.*;
import fviv.model.Employee.Departement;
import fviv.ticket.Ticket;
import fviv.ticket.TicketRepository;
import fviv.user.Roles;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.salespointframework.core.DataInitializer;
import org.salespointframework.useraccount.UserAccount;
import org.salespointframework.useraccount.UserAccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.joda.money.CurrencyUnit.EUR;



@Component
public class FvivDataInitializer implements DataInitializer {

	private final EmployeeRepository employeeRepository;
	private final UserAccountManager userAccountManager;
	private final FestivalRepository festivalRepository;
	private final TicketRepository ticketRepository;
	private final EventsRepository eventsRepository;
	private final ArtistsRepository artistsRepository;
	private final LocationRepository locationRepository;
	private final AreaItemsRepository areaItemsRepository;

	@Autowired
	public FvivDataInitializer(EmployeeRepository employeeRepository,
			UserAccountManager userAccountManager,
			AreaItemsRepository areaItemsRepository,
			TicketRepository ticketRepository,
			FestivalRepository festivalRepository,
			ArtistsRepository artistsRepository,
			EventsRepository eventsRepository,
			LocationRepository locationRepository) {
		Assert.notNull(employeeRepository,
				"EmployeeRepository must not be null!");
		this.employeeRepository = employeeRepository;
		this.userAccountManager = userAccountManager;
		this.ticketRepository = ticketRepository;
		this.festivalRepository = festivalRepository;
		this.artistsRepository = artistsRepository;
		this.eventsRepository = eventsRepository;
		this.areaItemsRepository = areaItemsRepository;
		this.locationRepository = locationRepository;
	}

	@Override
	public void initialize() {
		initializeUsers();
		initializeTickets();
		initializeLocations();
		try {
			initializeFestivals();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		initializeLineup();
	}

	private void initializeFestivals() throws ParseException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
		LocalDate date1 = LocalDate.parse("2014-12-30", formatter);
		LocalDate date2 = LocalDate.parse("2015-01-03", formatter);
		LocalDate date3 = LocalDate.parse("2015-01-05", formatter);
		LocalDate date4 = LocalDate.parse("2015-01-08", formatter);


		Festival festival1 = new Festival(date1, date2, "Wonderland", 1,
				"Avicii, Linkin Park", 20000, Money.of(EUR, 55));
		Festival festival2 = new Festival(date3, date4, "Rock am Ring", 2,
				"Netflix", 50000, Money.of(EUR, 55));

		festivalRepository.save(festival1);
		festivalRepository.save(festival2);

	}

	private void initializeLocations() {
		Location location1 = new Location("Tomorrowland", 400, 300, 20000,
				"Belgien, Flandern, Ort Boom", Money.of(EUR, 300.00));
		Location location2 = new Location("Rock am Ring", 200, 500, 50000,
				"Am Ring in ihrer Nähe", Money.of(EUR, 450.00));
		Location location3 = new Location("Woodstock", 2000, 3000,
				10000, "Woodstock, America", Money.of(EUR, 400.00));
		Location location4 = new Location("Brickleberry", 1000, 1400,
				9000, "Freizeitpark Brickleberry", Money.of(EUR, 350.00));
		Location location5 = new Location("Klassik Open Air", 5000, 3000, 5, "Wiener Straße, Wien, Österreich", Money.of(EUR, 500.00));

		locationRepository.save(location1);
		locationRepository.save(location2);
		locationRepository.save(location3);
		locationRepository.save(location4);
		locationRepository.save(location5);
	}

	private void initializeTickets() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd");
		LocalDate date = LocalDate.parse("2014-12-30", formatter);
		LocalDate date1 = LocalDate.parse("2014-12-31", formatter);

		// true(tages), already checked in
		Ticket ticket1 = new Ticket(true, false, "Wonderland", date);
		Ticket ticket3 = new Ticket(true, true, "Wonderland", date1);
		Ticket ticket4 = new Ticket(true, true, "Wonderland", date);
		Ticket ticket5 = new Ticket(true, true, "Wonderland", date);
		Ticket ticket6 = new Ticket(true, true, "Wonderland", date);
		Ticket ticket7 = new Ticket(false, true, "Wonderland", null);
		Ticket ticket8 = new Ticket(true, false, "Wonderland", date);

		Ticket ticke2 = new Ticket(false, true, "Rock am Ring", null);

		ticketRepository.save(ticket1);
		ticketRepository.save(ticke2);
		ticketRepository.save(ticket3);
		ticketRepository.save(ticket4);
		ticketRepository.save(ticket5);
		ticketRepository.save(ticket6);
		ticketRepository.save(ticket7);
		ticketRepository.save(ticket8);

	}

	private void initializeUsers() {
		UserAccount bossAccount = userAccountManager.create("boss", "123",
				Roles.boss, Roles.receiver, Roles.sender);
		bossAccount.setEmail("Boss@Fviv.de");
		bossAccount.setFirstname("Der");
		bossAccount.setLastname("Boss");
		UserAccount managerAccount = userAccountManager.create("manager",
				"123", Roles.manager);
		managerAccount.setEmail("Manager@Fviv.de");
		managerAccount.setFirstname("Der");
		managerAccount.setLastname("Manager");
		managerAccount.add(Roles.sender);
		managerAccount.add(Roles.receiver);
		UserAccount catererAccount = userAccountManager.create("caterer",
				"123", Roles.caterer);
		catererAccount.setEmail("Caterer@Fviv.de");
		catererAccount.setFirstname("Der");
		catererAccount.setLastname("Caterer");
		catererAccount.add(Roles.sender);
		catererAccount.add(Roles.receiver);
		UserAccount leaderAccount = userAccountManager.create("leader", "123",
				Roles.leader);
		leaderAccount.setEmail("Festivalleiter@Fviv.de");
		leaderAccount.setFirstname("Der");
		leaderAccount.setLastname("Festivalleiter");
		leaderAccount.add(Roles.sender);
		leaderAccount.add(Roles.receiver);
		UserAccount employeeAccount = userAccountManager.create("employee", "123",
				Roles.employee);
		employeeAccount.setEmail("employee@Fviv.de");
		employeeAccount.setFirstname("Der");
		employeeAccount.setLastname("Angestellte");
		employeeAccount.add(Roles.sender);
		employeeAccount.add(Roles.receiver);
		
		userAccountManager.save(bossAccount);
		userAccountManager.save(managerAccount);
		userAccountManager.save(catererAccount);
		userAccountManager.save(leaderAccount);
		userAccountManager.save(employeeAccount);
		
		// Create employees
		UserAccount employeeAccount1 = userAccountManager.create("gates",
				"123", Roles.employee);
		UserAccount employeeAccount2 = userAccountManager.create("merkel",
				"123", Roles.employee);
		UserAccount employeeAccount3 = userAccountManager.create("wurst",
				"123", Roles.employee);
		UserAccount employeeAccount4 = userAccountManager.create("white",
				"123", Roles.employee);
		UserAccount employeeAccount5 = userAccountManager.create("müller",
				"123", Roles.employee);

		Employee employee1 = new Employee(employeeAccount1, "Gates", "Bill",
				"Bill.Gates@Microsoft.com", "0190CallBill",
				Departement.MANAGEMENT);
		Employee employee4 = new Employee(employeeAccount4, "White", "Walter",
				"Walter.White@Kochkurse.de", "BetterCallSaul",
				Departement.MANAGEMENT);
		Employee employee5 = new Employee(employeeAccount5, "Müller", "Thomas",
				"Thomas.Müller@Weltmeister.de", "20304050",
				Departement.CATERING);

		// Save to repository
		employeeRepository.save(employee1);
		employeeRepository.save(employee4);
		employeeRepository.save(employee5);

		// Save to userAccountManager
		userAccountManager.save(employeeAccount1);
		userAccountManager.save(employeeAccount2);
		userAccountManager.save(employeeAccount3);
		userAccountManager.save(employeeAccount4);
		userAccountManager.save(employeeAccount5);
	}

	private void initializeLineup() {
		Artist artist = new Artist(100000, Money.of(CurrencyUnit.EUR, 20),
				"Dude", 20000);
		Artist artist2 = new Artist(1000000, Money.of(CurrencyUnit.EUR, 20),
				"Dudette", 20000);

		artistsRepository.save(artist);
		artistsRepository.save(artist2);

		Festival festival = festivalRepository.findOne((long) 1);
		Festival festival2 = festivalRepository.findOne((long) 2);

		AreaItem areaItem = new AreaItem(AreaItem.Type.STAGE, "Stage 1", 30, 30, 30, 30, festival);

		AreaItem areaItem2 = new AreaItem(AreaItem.Type.STAGE, "Stage 2", 30, 30, 10, 70, festival);

		areaItemsRepository.save(areaItem);
		areaItemsRepository.save(areaItem2);


		eventsRepository.save(new Event(LocalDateTime.of(2024, 12, 26, 1, 1, 1), LocalDateTime.of(2014, 12, 26, 1, 1, 0), artist, festival, areaItem));
		eventsRepository.save(new Event(LocalDateTime.of(2024, 12, 26, 1, 4, 1), LocalDateTime.of(2014, 12, 26, 1, 5, 0), artist2, festival, areaItem2));

		eventsRepository.save(new Event(LocalDateTime.of(2024, 12, 28, 1, 1, 1), LocalDateTime.of(2014, 12, 26, 1, 1, 0), artist, festival, areaItem));
		eventsRepository.save(new Event(LocalDateTime.of(2024, 12, 28, 1, 4, 1), LocalDateTime.of(2014, 12, 26, 1, 5, 0), artist2, festival, areaItem2));
		eventsRepository.save(new Event(LocalDateTime.of(2024, 12, 28, 1, 1, 1), LocalDateTime.of(2014, 12, 26, 1, 1, 0), artist, festival2, areaItem2));
		eventsRepository.save(new Event(LocalDateTime.of(2024, 12, 28, 1, 4, 1), LocalDateTime.of(2014, 12, 26, 1, 5, 0), artist2, festival2, areaItem2));
	}
}
