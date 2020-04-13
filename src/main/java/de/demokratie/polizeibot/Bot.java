package de.demokratie.polizeibot;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

/*****************************************************************************
 *                                                                           *
 *  Urheberrechtshinweis:                                                    *
 *   Copyright © Paul Stier, 2020                                            *
 *   Erstellt: 13.04.2020 / 15:00                                            *
 *                                                                           *
 *   Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.        *
 *   Das Urheberrecht liegt, soweit noch ausdrücklich anders gekennzeichnet, *
 *   bei Paul Stier. Alle Rechte vorbehalten.                                *
 *                                                                           *
 *   Jede Art von Vervielfältigung, Verbreitung, Vermietung, Verleihung,     *
 *   öffentliche Zugänglichmachung, Veränderung oder andere Nutzung          *
 *   bedarf der ausdrücklichen, schriftlichen Zustimmung von Paul Stier.     *
 *                                                                           *
 *****************************************************************************/

/*

    Class created by ce_phox on
    13.04.2020 15:00.
    
    Discord: ce_phox#1259

*/


public class Bot {

    public JDA jda;

    public Bot() {

        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken("<TOKEN>")
                    .setAutoReconnect(true)
                    .build().awaitReady();
        } catch (Exception ex) {
            System.err.println("Bot konnte sich nicht verbinden");
        }

    }

    public static void main(String[] args) {
        new Bot();
    }

}
