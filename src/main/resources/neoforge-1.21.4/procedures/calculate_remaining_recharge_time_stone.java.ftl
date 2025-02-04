<#include "mcitems.ftl">
		if (${mappedMCItemToItemStackCode(input$item, 1)}.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("rechargeStone") > 0) {
			{
				final String _tagName = "rechargeStone";
				final double _tagValue = (${mappedMCItemToItemStackCode(input$item, 1)}.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getDouble("rechargeStone") - 1);
				CustomData.update(DataComponents.CUSTOM_DATA, ${mappedMCItemToItemStackCode(input$item, 1)}, tag -> tag.putDouble(_tagName, _tagValue));
			}
	    }