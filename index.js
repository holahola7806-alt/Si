const { Client, GatewayIntentBits } = require('discord.js');

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
    const mensaje = '🔰 CREW SG ON TOP PERRAS 🔥';
    const repetido = Array(50).fill(mensaje).join('\n');
    
    await message.reply(repetido);
  }
});

client.on('error', console.error);
process.on('unhandledRejection', console.error);

client.login(process.env.TOKEN);
