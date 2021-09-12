package org.dennis.deliverybackend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.atomicMarkableReference;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dennis.deliverybackend.controller.DeliveryController;
import org.dennis.deliverybackend.model.PizzaListItem;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@AutoConfigureDataMongo
@SpringBootTest
@ActiveProfiles("dev")
class DeliverybackendApplicationTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	PizzaRepository pizzaRepository;

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
	void testPizzaRepository() {
		PizzaRepository repository = new PizzaRepository();
		List<Pizza> testItems = new LinkedList<Pizza>();
		testItems.add(new Pizza().setTitle("demo").setDescription("demo"));
		Pizza p1 = new Pizza().setTitle("demo1");
		testItems.add(p1);
		ReflectionTestUtils.setField(repository,"items",testItems);
		assertThat(repository.getList().size()).isEqualTo(testItems.size());
		assertThat(repository.getList().contains(p1)).isTrue();
	}

	@Test
	void testDeliveryControllerWeb() throws Exception {
		given(this.pizzaRepository.getList()).willReturn(List.of(new Pizza(), new Pizza()));
		List<PizzaListItem> responseList;
		MvcResult result = mvc.perform(get("/delivery/pizza")).andExpect(status().isOk()).andReturn();
		responseList =  new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<List<PizzaListItem>>() {});
		assertThat(responseList.size()).isGreaterThan(0);
	}

	@Test
	void testDeliveryController() throws Exception {
		List<PizzaListItem> responseList;
		PizzaRepository repository = new PizzaRepository();
		List<Pizza> testItems = new LinkedList<Pizza>();
		testItems.add(new Pizza().setTitle("demo").setDescription("demo").setPrice(1.23));
		Pizza p1 = new Pizza().setId(1).setTitle("demo1");
		testItems.add(p1);
		ReflectionTestUtils.setField(repository,"items",testItems);
		DeliveryController controller = new DeliveryController();
		ReflectionTestUtils.setField(controller,"repository",repository);
		responseList = controller.getAllPizzas();
		assertThat(responseList.size()).isEqualTo(repository.getList().size());
	}

	@Test
	void testPizzaDetailsWeb() throws Exception {
		given(this.pizzaRepository.getPizza(1)).willReturn(new Pizza().setId(1).setTitle("dummy"));
		MvcResult result = mvc.perform(get("/delivery/pizza/1")).andExpect(status().isOk()).andReturn();
		Pizza p =  new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<Pizza>() {});
		assertThat(p.getId()).isEqualTo(1);
		assertThat(p.getTitle()).isNotBlank();
	}



}
