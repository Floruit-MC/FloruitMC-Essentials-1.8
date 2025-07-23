# 📘 Essentials - Guia de Configurações e Comandos

O plugin **Essentials** é uma ferramenta essencial para qualquer servidor Minecraft. Ele oferece uma ampla variedade de funções para facilitar a administração, melhorar a jogabilidade e aumentar a proteção tanto para jogadores quanto para a equipe do servidor. Este guia apresenta as principais **configurações aplicadas** no servidor e uma lista de **comandos úteis**, com suas respectivas **permissões**.

---

## ⚙️ Configurações do Servidor

| Recurso                                     | Status                        |
| ------------------------------------------  | ----------------------------- |
| 🔇 Aparição natural de mobs                | Desativada                    |
| 🌧️ Clima (chuva/relâmpago)                 | Desativado                    |
| 🌞 Ciclo de dia/noite                      | Desativado (sempre dia)       |
| 🔥 Propagação de fogo                      | Desativada                    |
| 💣 Dano de explosões                       | Desativado                    |
| 🌊 Fluxo de lava e água                    | Desativado                    |
| ❄️ Gelo/neve se formando ou derretendo     | Desativado                    |
| 🛎️ Interação com camas                     | Bloqueada                     |
| 🚫 Comandos perigosos (/op, /reload, etc.) | Restritos aos administradores |
| 💀 Mensagens de morte                      | Ocultadas                     |

---

## 🛡️ Proteções Ativadas

| Tipo de Proteção                            | Status                             |
| ------------------------------------------- | ---------------------------------- |
| 🪂 Dano por queda e fome                    | Desativados                        |
| 🌵 Dano por cacto                           | Desativado                         |
| 🔥 Dano por fogo e lava                     | Desativado                         |
| 🫈 Sufocamento em blocos                    | Desativado                         |
| 🥳 Void (cair no vazio)                     | Teleporte automático para o /spawn |
| 🪨 Crafting de TNT, End Crystal e similares | Bloqueado                          |

---

## 💬 Comandos Disponíveis

### 🎮 Comandos Administrativos e de Jogo

| Comando      | Descrição                       | Permissão              |
| ------------ | ------------------------------- | ---------------------- |
| `/gamemode`  | Alterar modo de jogo            | `essentials.gamemode`  |
| `/tp`        | Teleportar para outro jogador   | `essentials.tp`        |
| `/fly`       | Ativar/desativar voo            | `essentials.fly`       |
| `/kill`      | Eliminar jogador ou entidade    | `essentials.kill`      |
| `/god`       | Ativar modo invencível          | `essentials.god`       |
| `/heal`      | Restaurar vida                  | `essentials.heal`      |
| `/feed`      | Restaurar fome                  | `essentials.feed`      |
| `/speed`     | Ajustar velocidade de movimento | `essentials.speed`     |
| `/clear`     | Limpar inventário               | `essentials.clear`     |
| `/give`      | Dar itens                       | `essentials.give`      |
| `/vanish`    | Tornar-se invisível             | `essentials.vanish`    |
| `/invsee`    | Ver/editar inventário de outro  | `essentials.invsee`    |
| `/hat`       | Usar bloco como chapéu          | `essentials.hat`       |
| `/repair`    | Reparar item                    | `essentials.repair`    |
| `/top`       | Teleportar para o topo          | `essentials.top`       |
| `/back`      | Voltar ao último local          | `essentials.back`      |
| `/lock`      | Trancar baús ou blocos          | `essentials.lock`      |
| `/unlock`    | Destrancar baús ou blocos       | `essentials.unlock`    |
| `/compactar` | Compactar itens em blocos       | `essentials.compactar` |

### 🔧 Comandos Úteis e Extras

| Comando      | Descrição                        | Permissão              |
| ------------ | -------------------------------- | ---------------------- |
| `/derreter`  | Fundir minérios automaticamente  | `essentials.derreter`  |
| `/luz`       | Criar fonte de luz temporária    | `essentials.luz`       |
| `/home`      | Criar e acessar casas            | `essentials.home`      |
| `/tpa`       | Pedir teleporte a outro jogador  | `essentials.tpa`       |
| `/ec`        | Acessar o baú do Ender           | `essentials.ec`        |
| `/bau`       | Baú virtual                      | `essentials.bau`       |
| `/check`     | Ver dados do jogador             | `essentials.check`     |
| `/alerta`    | Enviar alerta em destaque        | `essentials.alerta`    |
| `/cores`     | Usar cores no chat               | `essentials.cores`     |
| `/puxar`     | Puxar jogador até você           | `essentials.puxar`     |
| `/clearChat` | Limpar o chat do servidor        | `essentials.clearchat` |
| `/craft`     | Acessar mesa de trabalho virtual | `essentials.craft`     |
| `/divulgar`  | Enviar mensagem de anúncio       | `essentials.divulgar`  |
| `/slime`     | Ver regiões onde slimes nascem   | `essentials.slime`     |
| `/title`     | Enviar título na tela            | `essentials.title`     |
| `/head`      | Obter cabeças personalizadas     | `essentials.head`      |
| `/vip`       | Acessar menu de benefícios VIP   | `essentials.vip`       |
| `/thor`      | Invocar relâmpago                | `essentials.thor`      |
| `/rocket`    | Impulso vertical estilo foguete  | `essentials.rocket`    |
| `/sit`       | Sentar em qualquer lugar         | `essentials.sit`       |

---

## ✅ Informações Adicionais

As permissões devem ser configuradas com sistemas de gerenciamento como **LuckPerms**, **PermissionsEx** ou **GroupManager**.
É altamente recomendável limitar comandos avançados a jogadores confiáveis ou grupos administrativos, garantindo segurança e organização no servidor.
