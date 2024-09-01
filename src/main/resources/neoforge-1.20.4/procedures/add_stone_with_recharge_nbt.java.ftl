<#include "mcitems.ftl">
if (${input$entity} instanceof Player _player) {
	ItemStack _setstack = ${mappedMCItemToItemStackCode(input$item, 1)};
	_setstack.setCount(1);
	_setstack.getOrCreateTag().putDouble("rechargeStone", (!(entity instanceof Player _plr ? _plr.getAbilities().instabuild : false) ? (double) PowerConfigConfiguration.RECHARGE_TIMER.get() * 20 : 0));
	ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
}