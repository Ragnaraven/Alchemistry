package com.smashingmods.alchemistry.common.block.evaporator;

import com.smashingmods.alchemistry.api.blockentity.AbstractAlchemistryBlockEntity;
import com.smashingmods.alchemistry.api.container.AbstractAlchemistryMenu;
import com.smashingmods.alchemistry.registry.BlockRegistry;
import com.smashingmods.alchemistry.registry.MenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class EvaporatorMenu extends AbstractAlchemistryMenu {

    protected final ContainerData containerData;

    public EvaporatorMenu(int pContainerId, Inventory pInventory, FriendlyByteBuf pBuffer) {
        this(pContainerId, pInventory, Objects.requireNonNull(pInventory.player.level.getBlockEntity(pBuffer.readBlockPos())), new SimpleContainerData(6));
    }

    protected EvaporatorMenu(int pContainerId, Inventory pInventory, BlockEntity pBlockEntity, ContainerData pContainerData) {
        super(MenuRegistry.ATOMIZER_MENU.get(), pContainerId, pInventory, pBlockEntity, pContainerData, 1);
        this.containerData = pContainerData;

        checkContainerSize(pInventory, 1);

        AbstractAlchemistryBlockEntity blockEntity = ((AbstractAlchemistryBlockEntity) pBlockEntity);
        Level level = pInventory.player.level;

        blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler ->
                this.addSlot(new SlotItemHandler(handler, 0, 116, 35)));

        addDataSlots(pContainerData);
    }

    @Override
    public boolean stillValid(@Nonnull Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(Objects.requireNonNull(this.getBlockEntity().getLevel()), this.getBlockEntity().getBlockPos()), pPlayer, BlockRegistry.EVAPORTOR.get());
    }
}
