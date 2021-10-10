package org.dennis.deliverybackend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dennis.deliverybackend.controller.DeliveryController;
import org.dennis.deliverybackend.model.Pizza;
import org.dennis.deliverybackend.model.PizzaListItem;
import org.dennis.deliverybackend.repository.PizzaRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
@ActiveProfiles("dev")
class DeliverybackendApplicationTests {

	@Autowired
	MockMvc mvc;

	@Mock
	PizzaRepository pizzaMongoRepository;

	@Autowired
	DeliveryController deliveryController;

	@Test
	void testPizzaModel() {
		final String title="demotitle";
		final String description="demodesc";
		final double price=1.23;
		final int id = 2;
		Pizza pizza = Pizza.builder().id(id).title(title).description(description).price(price).build();
		assertThat(pizza.getTitle()).isEqualTo(title);
		assertThat(pizza.getDescription()).isEqualTo(description);
		assertThat(pizza.getPrice()).isEqualTo(price);
		assertThat(pizza.getId()).isEqualTo(id);
	}

	@Test
	void testPizzaListItem() {
		final int id=1;
		PizzaListItem pizzaListItem = new PizzaListItem();
		pizzaListItem.setId(id);
		assertThat(id).isEqualTo(pizzaListItem.getId());
	}

	@Test
	void testDeliveryControllerListWeb() throws Exception {
		given(this.pizzaMongoRepository.findAll()).willReturn(List.of(new Pizza(), new Pizza()));
		List<PizzaListItem> responseList;
		ReflectionTestUtils.setField(deliveryController, "pizzaMongoRepository", this.pizzaMongoRepository);
		MvcResult result = mvc.perform(get("/delivery/pizza")).andExpect(status().isOk()).andReturn();
		responseList =  new ObjectMapper().readValue(result.getResponse().getContentAsString(), new TypeReference<List<PizzaListItem>>() {});
		assertThat(responseList.size()).isGreaterThan(0);
	}

	@Test
	void testDeliveryControllerList() throws Exception {
		List<PizzaListItem> responseList;
		List<Pizza> testItems = new LinkedList<Pizza>();
		testItems.add(Pizza.builder().title("demo").description("demo").price(1.23).build());
		Pizza p1 = Pizza.builder().id(1).title("demo1").build();
		testItems.add(p1);
		given(this.pizzaMongoRepository.findAll()).willReturn(testItems);
		DeliveryController deliveryControllerLocal = new DeliveryController();
		ReflectionTestUtils.setField(deliveryControllerLocal, "pizzaMongoRepository", this.pizzaMongoRepository);
		responseList = deliveryControllerLocal.getAllPizzas();
		assertThat(responseList.size()).isEqualTo(testItems.size());
	}

	@Test
	void testPizzaDetailsWeb() throws Exception {
		given(this.pizzaMongoRepository.findById(1)).willReturn(Pizza.builder().id(1).title("dummy").build());
		ReflectionTestUtils.setField(deliveryController, "pizzaMongoRepository", this.pizzaMongoRepository);
		MvcResult result = mvc.perform(get("/delivery/pizza/1")).andExpect(status().isOk()).andReturn();
		Pizza p =  new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(result.getResponse().getContentAsString(), new TypeReference<Pizza>() {});
		assertThat(p.getId()).isEqualTo(1);
		assertThat(p.getTitle()).isNotBlank();
	}




}
