const { Client, GatewayIntentBits } = require('discord.js');
require('dotenv').config();

const PREFIX = process.env.PREFIX || '!';

const client = new Client({
  intents: [
    GatewayIntentBits.Guilds,
    GatewayIntentBits.GuildMessages,
    GatewayIntentBits.MessageContent
  ]
});

client.on('ready', () => {
  console.log(`✅ Conectado como: ${client.user.tag}`);
});

client.on('messageCreate', async message => {
  if (!message.guild || message.author.bot) return;

  if (message.content.trim() === `${PREFIX}xd`) {
    await message.reply(`
🔰 CREW SG ON TOP PERRAS 🔥
💪 NADIE NOS IGUALA
👑 SIEMPRE ENCIMA
🔥 CREW SG SIEMPRE
    `);
  }
});

client.on('error', console.error);
process.on('unhandledRejection', console.error);

client.login(process.env.TOKEN);
