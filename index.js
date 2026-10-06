const { Client, GatewayIntentBits } = require('discord.js');
require('dotenv').config();

const client = new Client({
  intents: [
    GatewayIntentBits.Guilds,
    GatewayIntentBits.GuildMessages,
    GatewayIntentBits.MessageContent
  ]
});

client.on('clientReady', () => {
  console.log(`✅ Conectado como: ${client.user.tag}`);
});

client.on('messageCreate', async message => {
  if (!message.guild || message.author.bot) return;

  if (message.content.trim() === '!ñop') {
    await message.reply('🔰 CREW SG ON TOP PERRAS 🔥💪🔰 CREW SG ON TOP PERRAS 🔥💪🔰 CREW SG ON TOP PERRAS 🔥💪🔰 CREW SG ON TOP PERRAS 🔥💪🔰 CREW SG ON TOP PERRAS 🔥💪🔰 CREW SG ON TOP PERRAS 🔥💪🔰 CREW SG ON TOP PERRAS 🔥💪🔰 CREW SG ON TOP PERRAS 🔥💪');
  }
});

client.login(process.env.TOKEN);
