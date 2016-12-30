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
package com.qcadoo.mes.masterOrders.listeners;

import com.google.common.collect.Maps;
import com.qcadoo.mes.masterOrders.constants.MasterOrderFields;
import com.qcadoo.mes.masterOrders.constants.MasterOrderProductFields;
import com.qcadoo.mes.masterOrders.constants.MasterOrderType;
import com.qcadoo.mes.masterOrders.constants.MasterOrdersPositionFields;
import com.qcadoo.mes.masterOrders.hooks.MasterOrderDetailsHooks;
import com.qcadoo.mes.orders.TechnologyServiceO;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.ExpressionService;
import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.*;
import com.qcadoo.view.api.ribbon.RibbonActionItem;
import com.qcadoo.view.api.ribbon.RibbonGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class MasterOrderPositionDetailsListeners {

    private static final String L_WINDOW_ACTIVE_MENU = "window.activeMenu";
    private static final String L_GRID = "grid";

    public void createOrder(final ViewDefinitionState view, final ComponentState state, final String[] args) {
        GridComponent masterOrderPositionComponent = (GridComponent) view.getComponentByReference(L_GRID);
        List<Entity> selectedEntity = masterOrderPositionComponent.getSelectedEntities();
        if(selectedEntity.size() != 1){
            state.addMessage("masterOrders.masterOrder.masterOrdersPosition.MoreEntitiesSelectedThanAllowed",ComponentState.MessageType.INFO);
            return;
        }else if(selectedEntity.size() == 0){
            state.addMessage("masterOrders.masterOrder.masterOrdersPosition.LessEntitiesSelectedThanAllowed",ComponentState.MessageType.INFO);
            return;
        }
        Entity masterOrderPosition = selectedEntity.get(0);
        Integer masterOrderId = masterOrderPosition.getIntegerField(MasterOrdersPositionFields.MASTER_ORDER_ID);
        if (masterOrderId == null) {
            return;
        }

        Map<String, Object> parameters = Maps.newHashMap();
        parameters.put("form.masterOrder", masterOrderId);

        String masterOrderTypeValue = masterOrderPosition.getStringField(MasterOrdersPositionFields.MASTER_ORDER_TYPE);


        if (masterOrderTypeValue.equals(MasterOrderType.MANY_PRODUCTS.getStringValue())) {
            Integer productId = masterOrderPosition.getIntegerField(MasterOrdersPositionFields.PRODUCT_ID);
            Integer masterOrderProductId = masterOrderPosition.getIntegerField(MasterOrdersPositionFields.MASTER_ORDER_PRODUCT_ID);
            parameters.put("form.masterOrderProduct", productId);
            parameters.put("form.masterOrderProductComponent", masterOrderProductId);
        }



        parameters.put(L_WINDOW_ACTIVE_MENU, "orders.productionOrders");

        String url = "../page/orders/orderDetails.html";
        view.redirectTo(url, false, true, parameters);
    }

}