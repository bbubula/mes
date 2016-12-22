/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo MES
 * Version: 1.4
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.mes.masterOrders.hooks;

import com.qcadoo.mes.masterOrders.constants.MasterOrderFields;
import com.qcadoo.mes.masterOrders.constants.MasterOrderType;
import com.qcadoo.mes.masterOrders.constants.MasterOrdersConstants;
import com.qcadoo.mes.masterOrders.criteriaModifier.OrderCriteriaModifier;
import com.qcadoo.mes.masterOrders.util.MasterOrderOrdersDataProvider;
import com.qcadoo.mes.orders.TechnologyServiceO;
import com.qcadoo.model.api.BigDecimalUtils;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.ExpressionService;
import com.qcadoo.model.api.NumberService;
import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ComponentState.MessageType;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.*;
import com.qcadoo.view.api.ribbon.RibbonActionItem;
import com.qcadoo.view.api.ribbon.RibbonGroup;
import com.qcadoo.view.api.utils.NumberGeneratorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.qcadoo.mes.basic.constants.ProductFields.UNIT;
import static com.qcadoo.mes.masterOrders.constants.MasterOrderFields.*;

@Service
public class MasterOrdersPositionHooks {


    private static final String L_WINDOW = "window";
    private static final String L_GRID = "grid";


    public void disableButton(final ViewDefinitionState view) {
        WindowComponent window = (WindowComponent) view.getComponentByReference(L_WINDOW);
        RibbonGroup group = window.getRibbon().getGroupByName("orders");
        RibbonActionItem button = group.getItemByName("createOrder");

        GridComponent masterOrderPositionComponent = (GridComponent) view.getComponentByReference(L_GRID);
        List<Entity> selectedEntity = masterOrderPositionComponent.getSelectedEntities();
        if(selectedEntity.size() == 1){
            button.setEnabled(true);
            button.requestUpdate(true);
            window.requestRibbonRender();
        }else{
            button.setEnabled(false);
            button.requestUpdate(true);
            window.requestRibbonRender();
        }

    }

}
