package com.qcadoo.mes.plugins.products.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.qcadoo.mes.core.data.api.DataAccessService;
import com.qcadoo.mes.core.data.api.DataDefinitionService;
import com.qcadoo.mes.plugins.products.data.mock.DataAccessServiceMock;
import com.qcadoo.mes.plugins.products.data.mock.DataDefinitionServiceMock;

public class ProductsModifyControllerAddModifyEntityForm {

	private ProductsModifyController controller;

	private DataDefinitionService dds;
	private DataAccessService das;

	@Before
	public void setUp() {
		dds = new DataDefinitionServiceMock();
		das = new DataAccessServiceMock();
		controller = new ProductsModifyController(dds, das);
	}

	@Test
	public void shouldShowEmptyForm() {
		// given

		// when
		ModelAndView modelAndView = controller.addModifyEntityForm(null);
		// then
		assertEquals("addModifyEntity", modelAndView.getViewName());
		assertNotNull(modelAndView.getModel().get("headerContent"));
		assertEquals("Produkt:", modelAndView.getModel().get("headerContent"));
		assertNotNull(modelAndView.getModel().get("fieldsDefinition"));

	}

	@Test
	public void shouldShowFilledFormWithDataFromEditedEntity() {
		// given

		// when
		ModelAndView modelAndView = controller.addModifyEntityForm("1");
		// then
		assertEquals("addModifyEntity", modelAndView.getViewName());
		assertNotNull(modelAndView.getModel().get("headerContent"));
		assertEquals("Produkt:", modelAndView.getModel().get("headerContent"));
		assertNotNull(modelAndView.getModel().get("fieldsDefinition"));
		assertEquals(das.get("product", Long.parseLong("1")).getFields(),
				modelAndView.getModel().get("entity"));

	}

}
