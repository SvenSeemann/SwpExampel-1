package fviv.controller;

import javax.validation.Valid;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fviv.model.Employee;
import fviv.model.EmployeeRepository;
import fviv.model.Expense;
import fviv.model.ExpenseRepository;
import fviv.model.Registration;
import fviv.model.Ticket;
import fviv.model.TicketRepository;
import fviv.model.Ticketmaker;

@Controller
class ManagerFunctionsController {
	private final EmployeeRepository employeeRepository;
	private final ExpenseRepository expenseRepository;
	private final TicketRepository ticketRepository;

	@Autowired
	public ManagerFunctionsController(EmployeeRepository employeeRepository,
			ExpenseRepository expenseRepository,
			TicketRepository ticketrepository) {
		this.employeeRepository = employeeRepository;
		this.expenseRepository = expenseRepository;
		this.ticketRepository = ticketrepository;
	}

	@RequestMapping({ "/", "/manager" })
	public String index() {
		return "manager";
	}

	@RequestMapping("/checkEmployees")
	public String checkEmployees(ModelMap modelMap) {
		modelMap.addAttribute("employeelist", employeeRepository.findAll());
		modelMap.addAttribute("registration", new Registration());
		return "checkEmployees";
	}

	@RequestMapping("/ticket")
	public String ticket(ModelMap modelMap) {
		modelMap.addAttribute("ticketlist", ticketRepository.findAll());
		modelMap.addAttribute("ticketmaker", new Ticketmaker());
		return "ticket";
	}

	@RequestMapping("/newEmployee")
	public String newEmployee(
			@ModelAttribute(value = "registration") @Valid Registration registration,
			BindingResult results) {
		// Return to checkEmployees if new employee data are invalid or
		// incomplete
		if (results.hasErrors())
			return "redirect:/checkEmployees";

		// Create new employee
		Employee employee = new Employee(registration.getLastname(),
				registration.getFirstname(), registration.getEmail(),
				registration.getPhone());
		employeeRepository.save(employee);

		return "redirect:/checkEmployees";
	}

	@RequestMapping("/newTicket")
	public String newTicket(
			@ModelAttribute(value = "registration") @Valid Ticketmaker ticketmaker,
			BindingResult results) {
		// Return to Tickets if new employee data are invalid or incomplete
		if (results.hasErrors())
			return "redirect:/ticket";

		// Create new employee
		Ticket ticket = new Ticket(ticketmaker.getTicketart(),
				ticketmaker.getFestivalname(), ticketmaker.getActors());
		ticketRepository.save(ticket);

		return "redirect:/Ticket";
	}

	@RequestMapping("/checkFinances")
	public String checkFinances(ModelMap modelMap) {
		float salaryTotal = 0, cateringTotal = 0, rentTotal = 0, deposit = 0;
		modelMap.addAttribute("salary",
				expenseRepository.findByExpenseType("salary"));
		modelMap.addAttribute("catering",
				expenseRepository.findByExpenseType("catering"));
		modelMap.addAttribute("rent",
				expenseRepository.findByExpenseType("rent"));

		for (Expense exp : expenseRepository.findByExpenseType("salary")) {
			salaryTotal += exp.getAmount();
		}

		for (Expense exp : expenseRepository.findByExpenseType("catering")) {
			cateringTotal += exp.getAmount();
		}

		for (Expense exp : expenseRepository.findByExpenseType("rent")) {
			rentTotal += exp.getAmount();
		}

		for (Expense exp : expenseRepository.findByExpenseType("deposit")) {
			deposit += exp.getAmount();
		}

		modelMap.addAttribute("deposit", deposit);
		modelMap.addAttribute("salaryTotal", salaryTotal);
		modelMap.addAttribute("cateringTotal", cateringTotal);
		modelMap.addAttribute("rentTotal", rentTotal);
		return "checkFinances";
	}

	@RequestMapping("/accountAdministration")
	public String newLogin(ModelMap modelMap) {
		return "accountAdministration";
	}

}
