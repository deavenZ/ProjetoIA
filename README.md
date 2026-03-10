# ProjetoIA - Inteligência Artificial em Jogos Clássicos

## Descrição

Projeto desenvolvido para a disciplina de Inteligência Artificial do 2º semestre do 2º ano. O projeto demonstra a aplicação de técnicas de aprendizagem automática para treinar agentes capazes de jogar e obter bom desempenho em dois jogos clássicos: **Breakout** e **Pacman**.

## Tecnologias e Técnicas Utilizadas

- **Redes Neurais Feedforward**: Implementação de uma rede neural com uma camada oculta para controlar os agentes
- **Algoritmos Genéticos**: Evolução de populações de redes neurais para otimizar o seu desempenho
- **Java**: Linguagem de programação utilizada

## Estrutura do Projeto

```
ProjetoIA/
├── src/
│   ├── algorithms/
│   │   ├── FeedforwardNeuralNetwork.java    # Implementação da rede neural
│   │   └── GeneticAlgorithm.java             # Implementação do algoritmo genético
│   ├── breakout/
│   │   ├── Main.java                         # Ponto de entrada para Breakout
│   │   ├── Breakout.java                     # Classe principal do jogo
│   │   ├── BreakoutBoard.java                # Tabuleiro do jogo
│   │   ├── BreakoutGeneticAlgorithm.java     # GA aplicado a Breakout
│   │   ├── BreakoutNN.java                   # Rede neural para Breakout
│   │   ├── Ball.java                         # Implementação da bola
│   │   ├── Paddle.java                       # Implementação da raquete
│   │   ├── Brick.java                        # Blocos do jogo
│   │   └── Sprite.java                       # Classe base para sprites
│   ├── pacman/
│   │   ├── Main.java                         # Ponto de entrada para Pacman
│   │   ├── Pacman.java                       # Classe principal do jogo
│   │   ├── PacmanBoard.java                  # Tabuleiro do jogo
│   │   ├── PacmanGeneticAlgorithm.java       # GA aplicado a Pacman
│   │   ├── PacmanNN.java                     # Rede neural para Pacman
│   │   └── RandomController.java             # Controlador com movimentos aleatórios
│   └── utils/
│       ├── Commons.java                      # Constantes e utilitários
│       └── GameController.java               # Interface para controladores
├── bin/                                      # Ficheiros compilados
├── images/                                   # Recursos de imagens
└── README.md
```

## Componentes Principais

### 1. Rede Neural Feedforward (FeedforwardNeuralNetwork.java)

Implementa uma arquitetura de rede neural com:
- **Camada de entrada**: Recebe o estado do jogo
- **Camada oculta**: Processa a informação
- **Camada de saída**: Produz a ação a realizar

**Parâmetros:**
- Tamanho de entrada: Variável (depende do jogo)
- Neurónios na camada oculta: 50
- Tamanho de saída: 1 (ação contínua)

### 2. Algoritmo Genético (GeneticAlgorithm.java)

Implementa evolução de redes neurais através de:
- **Tamanho da população**: 100 indivíduos
- **Número de gerações**: 100
- **Taxa de mutação**: 0.1 (10%)
- **Seleção**: Baseada em fitness
- **Crossover**: Cruzamento entre os melhores indivíduos

### 3. Breakout

Jogo clássico de destruição de blocos controlado por uma rede neural treinada.

**Como executar:**
```bash
javac -d bin src/algorithms/*.java src/utils/*.java src/breakout/*.java
java -cp bin breakout.Main
```

### 4. Pacman

Jogo clássico de Pacman onde o agente é controlado por uma rede neural treinada.

**Como executar:**
```bash
javac -d bin src/algorithms/*.java src/utils/*.java src/pacman/*.java
java -cp bin pacman.Main
```

## Como Usar

### Compilação

Compile todos os ficheiros Java:
```bash
javac -d bin src/**/*.java
```

### Execução

**Jogar Breakout com IA treinada:**
```bash
java -cp bin breakout.Main
```

**Jogar Pacman com IA treinada:**
```bash
java -cp bin pacman.Main
```

## Fluxo de Funcionamento

### Treinamento com Algoritmo Genético

1. **Inicialização**: Cria uma população inicial de redes neurais com pesos aleatórios
2. **Avaliação**: Testa cada indivíduo jogando uma partida completa e regista a aptidão
3. **Seleção**: Seleciona os melhores indivíduos da população
4. **Crossover**: Cria novos indivíduos combinando pesos dos pais
5. **Mutação**: Introduz pequenas variações aleatórias para explorar o espaço de soluções
6. **Repetição**: Repete o processo por 100 gerações

### Inferência

A rede neural treinada recebe o estado atual do jogo como entrada e:
1. Processa a informação através da camada oculta (50 neurónios)
2. Produz uma ação de saída contínua
3. A ação é convertida no movimento do agente no jogo

## Métricas de Desempenho

O desempenho de cada agente é avaliado através do **fitness**, que pode incluir:
- Número de blocos destruídos (Breakout)
- Número de fantasmas apanhados (Pacman)
- Tempo de sobrevivência
- Outros critérios específicos do jogo

## Configuração e Ajustes

Para adaptar o treino aos seus objetivos, pode modificar os seguintes parâmetros:

1. **Tamanho da população**: Alterar `POPULATION_SIZE` em `GeneticAlgorithm.java` (aumentar para melhor exploração, diminuir para treinos mais rápidos)
2. **Taxa de mutação**: Ajustar `MUTATION_RATE` em `GeneticAlgorithm.java` (valores mais elevados permitem maior exploração)
3. **Número de gerações**: Modificar `NUM_GENERATIONS` em `GeneticAlgorithm.java` (mais gerações = melhor convergência)
4. **Arquitetura da rede**: Ajustar `hiddenDim` em `FeedforwardNeuralNetwork.java` (mais neurónios = maior capacidade, mas treino mais lento)

## Requisitos do Sistema

- Java 8 ou superior
- Pelo menos 2GB de RAM (para execução com populações de 100 indivíduos)

## Autores

Projeto desenvolvido para a disciplina de Inteligência Artificial - ISCTE

## Licença

Este projeto é fornecido apenas para fins educacionais.

---

## Notas Adicionais

- O projeto utiliza seeds para reprodutibilidade dos resultados
- Cada jogo implementa a interface `GameController` para padronização
- As constantes do projeto estão centralizadas em `Commons.java`
