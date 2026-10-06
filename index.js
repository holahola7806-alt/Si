const { Client, GatewayIntentBits } = require('discord.js');
require('dotenv').config();

const client = new Client({
  intents: [
    GatewayIntentBits.Guilds,
    GatewayIntentBits.GuildMessages,
    GatewayIntentBits.MessageContent
  ]
});

client.on('ready', () => {
  console.log(`✅ CONECTADO: ${client.user.tag}`);
});

client.on('messageCreate', async message => {
  if (!message.guild || message.author.bot) return;

  if (message.content === '!xd') {
    await message.reply('🔰 CREW SG ON TOP PERRAS 🔥');
  }
});

client.login(process.env.TOKEN);
