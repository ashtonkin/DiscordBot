import database.Database
import listeners.JoinListener
import listeners.MessageReactionListener
import listeners.StatusListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DiscordBot {

    private val db: Database = Database("discordBot", "discordBot", "discordBot")

    companion object {
        val logger: Logger = LoggerFactory.getLogger(DiscordBot::class.java)
    }

    // Get token from bot.properties
    private val token: String = System.getProperty("TOKEN")

    private val jda = JDABuilder.create(
        db.getInfo("token", "discordBot", "name = token"),
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
        db.insert("discordBot", listOf("name", "value"), listOf("token", token))
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

    //TODO:
    // - Add token to database
    // - Add database to project URL
    // - Setup Announcement command for staff only
}