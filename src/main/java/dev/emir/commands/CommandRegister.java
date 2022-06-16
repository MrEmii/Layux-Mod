package dev.emir.commands;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class CommandRegister {
    public static void register(){
        CommandRegistrationCallback.EVENT.register(HomeCommand::registerCommand);
    }
}
