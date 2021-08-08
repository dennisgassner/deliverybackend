package org.dennis.deliverybackend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.atomicMarkableReference;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dennis.deliverybackend.controller.DeliveryController;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.dennis.deliverybackend.model.Pizza;
import org.dennis.deliverybackend.repository.PizzaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("dev")
class DeliverybackendApplicationTests {

	@Autowired
	MockMvc mvc;

	@Test
	void contextLoads() {
	}

	@Test
	void testPizza() {
		final String title="demotitle";
		final String description="demodesc";
		final double price=1.23;
		final int id = 2;
		Pizza pizza = new Pizza();
		pizza.setId(id).setTitle(title).setDescription(description).setPrice(price);
		assertThat(pizza.getTitle()).isEqualTo(title);
		assertThat(pizza.getDescription()).isEqualTo(description);
		assertThat(pizza.getPrice()).isEqualTo(price);
		assertThat(pizza.getId()).isEqualTo(id);
	}

	@Test
	void testPizzaRespository() {
		PizzaRepository repository = new PizzaRepository();
		List<Pizza> testItems = new LinkedList<Pizza>();
		testItems.add(new Pizza().setTitle("demo").setDescription("demo"));
		Pizza p1 = new Pizza().setTitle("demo1");
		testItems.add(p1);
		ReflectionTestUtils.setField(repository,"items",testItems);
		assertThat(repository.getAll().size()).isEqualTo(testItems.size());
		assertThat(repository.getAll().contains(p1)).isTrue();
	}

	@Test
	void testDeliveryControllerWeb() throws Exception {
		List<Pizza> responseList;
		MvcResult result = mvc.perform(get("/delivery/list_all")).andExpect(status().isOk()).andReturn();
		responseList =  new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<List<Pizza>>() {});
		assertThat(responseList.size()).isGreaterThan(0);
	}

	@Test
	void testDeliveryController() throws Exception {
		List<Pizza> responseList;
		//demo repository
		PizzaRepository repository = new PizzaRepository();
		List<Pizza> testItems = new LinkedList<Pizza>();
		testItems.add(new Pizza().setTitle("demo").setDescription("demo").setPrice(1.23));
		Pizza p1 = new Pizza().setId(1).setTitle("demo1");
		testItems.add(p1);
		ReflectionTestUtils.setField(repository,"items",testItems);
		DeliveryController controller = new DeliveryController();
		ReflectionTestUtils.setField(controller,"repository",repository);
		responseList = controller.getAllPizzas();
		assertThat(responseList.size()).isEqualTo(repository.getAll().size());
		assertThat(responseList.contains(p1)).isTrue();
	}



}
