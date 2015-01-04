package fviv.controller;

import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fviv.areaPlanner.Coords;
import fviv.areaPlanner.MList;
import fviv.areaPlanner.PlanningRepository;
import fviv.areaPlanner.Coords.Type;

@RestController
public class PlanningAJAXController {
	private static final String IS_AJAX_HEADER = "X-Requested-With=XMLHttpRequest";

	private PlanningRepository planningRepository;

	@Autowired
	public PlanningAJAXController(PlanningRepository planningRepository) {
		super();
		this.planningRepository = planningRepository;
	}

	@RequestMapping(value = "/newArea", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public boolean newAreal(@RequestParam("width") int width,
			@RequestParam("height") int height,
			@RequestParam("faktor") float factor) {
		Coords area = planningRepository.findByName("Areal");
		if (area == null) {
			planningRepository.save(new Coords(Type.AREA, "Areal", width,
					height, 0, 0, factor));
			return true;
		} else {
			System.out.println("weite: " + area.getWidth() + "hoehe: "
					+ area.getHeight() + "faktor: " + area.getFactor());
			return false;
		}
	}

	@RequestMapping(value = "/newObject", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public boolean newObject(@RequestParam("typ") String typ,
			@RequestParam("name") String name,
			@RequestParam("width") int width,
			@RequestParam("height") int height,
			@RequestParam("left") float left, @RequestParam("top") float top) {
		if (planningRepository.findByName("Areal") != null) {
			switch (typ) {
			case "TOILET":
				planningRepository.save(new Coords(Type.TOILET, (name),
						width, height, left, top));
				break;
			case "STAGE":
				planningRepository.save(new Coords(Type.STAGE, (name),
						width, height, left, top));
				break;
			case "CATERING":
				planningRepository.save(new Coords(Type.CATERING, (name),
						width, height, left, top));
				break;
			case "CAMPING":
				planningRepository.save(new Coords(Type.CAMPING, (name),
						width, height, left, top));
			}
		}
		showMe();
		// System.out.println(typ + ", " + name + ", " + width + ", " + height
		// + ", " + left + ", " + top);
		return true;
	}

	public void showMe() {
		Iterable<Coords> all = planningRepository.findAll();
		Coords area = planningRepository.findByName("Areal");
		System.out.println("weite: " + area.getWidth() + "hoehe: "
				+ area.getHeight());
	}
	
	@RequestMapping(value = "/terminal", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public Iterable<Coords> giveMeAllEntries(@RequestParam("request") String request) {
		return planningRepository.findAll();
	}
}
