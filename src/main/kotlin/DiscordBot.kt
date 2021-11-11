import database.Database
import listeners.JoinListener
import listeners.MessageReactionListener
import listeners.StatusListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class DiscordBot {

    private val db: Database = Database("discordBot", "root", "password")

    companion object {
        val logger: Logger = LoggerFactory.getLogger(DiscordBot::class.java)
    }

    init {
        val props = Properties()
        props.load(DiscordBot::class.java.getResourceAsStream("/bot.properties"))
        val token = props.getProperty("TOKEN")
        db.insert("private", mapOf("name" to "discord_bot_token", "value" to token))
    }

    private val jda = JDABuilder.create(
        db.getValue("private", "value", "name = 'discord_bot_token'"),
        listOf(
            GatewayIntent.GUILD_BANS,
            GatewayIntent.GUILD_EMOJIS,
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MESSAGE_REACTIONS
        )
    )
        .disableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGE_TYPING)
        .addEventListeners(
            StatusListener()
        )
        .build()

    init {
        jda.awaitReady()
        db.getConnection()
        logger.info("Bot is running!")
        registerListeners()
    }

    private fun registerListeners() {
        val listeners = listOf(
            MessageReactionListener(),
            JoinListener()
        )
        logger.info("Registering ${listeners.size} listeners..")
        listeners.forEach { jda.addEventListener(it) }
    }
}