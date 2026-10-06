// ═══════════════════════════════════════════════════════════════
// 🔰 CREW SG — BOT SIMPLE
// ═══════════════════════════════════════════════════════════════

const {
  Client,
  GatewayIntentBits
} = require('discord.js');
require('dotenv').config();

const client = new Client({
  intents: [
    GatewayIntentBits.Guilds,
    GatewayIntentBits.GuildMessages,
    GatewayIntentBits.MessageContent
  ]
});

const PREFIX = '!';

client.on('ready', () => {
  console.log(`✅ Conectado como: ${client.user.tag}`);
});

client.on('messageCreate', async message => {
  if (!message.guild || message.author.bot) return;

  if (message.content === `${PREFIX}ñop`) {
    await message.reply('Sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo Sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo Sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo sexo');
  }
});

client.login(process.env.TOKEN);
