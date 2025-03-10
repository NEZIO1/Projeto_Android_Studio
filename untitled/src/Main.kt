fun main() {
    val name = "FIAP"
    println("Hello, " + name + "!")

//Aqui aparece a quantidade de Caracteres que tem na variavel Name
    println(name.length)

    var value1 = 500       //Aqui, a inferência é para Int
    var value2: Int = 500  //Declaração explícita
// Apresentando o value1
    println(value1)
// Apresentando o value2
    println(value2)

//Forma de mostrar o valor máximo aceito pelo tipo
    println(Int.MAX_VALUE)
//Forma de mostrar o valor mínimo aceito pelo tipo
    println(Int.MIN_VALUE)

//Criando uma variavel do tipo Pair (Par)
    var (codigo, descricao) = Pair(120, "Mano")
    println(codigo)
    println(descricao)

//Para utilizar o valor null (nulo) e nesesario colocar uma (?) no Int
    /*var idade: Int? = null
    println(idade)

    idade = 18
    println(idade)

//Criando uma Lista de Cidades o primeiro numero comesa com 0 que no caso seria Sao Paulo
    var cidades= arrayOf("Sao Paulo", "Minas Gerais", "Rio de Janeiro")
    println(cidades[1])
    println(cidades[2])
//Modificando a lista trocando Rio de Janeiro para Florianopolis
    cidades[2] = "Florianopolis"
    println(cidades[2])
//Perguntando se a cidade estar vazia com (Verdadeiro ou Falso)
    var temCidade = cidades.isEmpty()
    println(temCidade)
//Perguntando quantas cidade tem na lista
    println(cidades.size)

//Adicionando mais elemantos na lista
    var frutas = ArrayList<String>()
    println("Frutas " + frutas.size)
    frutas.add("Banana")
    frutas.add("Melancia")
    println("Frutas " + frutas.size)
//Perguntando se na lista tem Uva
    println(frutas.contains("Uva"))
// Visualizar todos os itens da lista
    println(frutas)
//Pedindo para remolvar Banana da lista
    frutas.remove("Banana")
    println(frutas)

// setOF nao pode adicionar outro item em baixo ex:(precos.add) sem item repitidos
    var precos = setOf(45.9, 32.2, 20.4)
    println(precos)

//Fazendo uma lista com Nomes e Valores
    var produtos = HashMap<String,Double>()
    produtos.put("Mouse", 145.9)
    produtos.put("Teclado", 399.9)
    produtos.put("Fone", 230.4)

    println(produtos)
//Removendo um item
    produtos.remove("Mouse")
    println(produtos)
//Perguntado o Preso do Teclado
    println(produtos.get("Teclado"))


// Operadores
    var a = 10
    var b = 20;
    var c = a + b
    println("Soma $c")

    c = a - b
    println("Subtraçao $c")

    c = a * b
    println("Divisao $c")

    c = a % 3
    println("Resto $c")

// Uma forma mehlor de siplificar isso (a = a + 5)
    a += 5
    a -= 2
    a /= 3
    a *= 3
    a %= 2
    println("Soma Composta $a")


    println(2 > 5)
    println(2 < 5)
    println(2 == 5)
    println(2 == 2)
// ! Pergunta se 2 e diferente de 5
    println(2 != 5)
    println(2 >= 2)
// Fazendo a tabela Verdade Com && (Todas as esprecão tem que ser Verdadeira = True)
    println(2 < 3 && 5 > 4)
// Fazendo a tabela Verdade Com OU = ||  ( Se uma espresao for Verdade no final vai fiacar = True(verdade))
    println(2 > 3 || 5 == 5)*/

//Verificando a idade
    var idade = 21
    if(idade >= 18){
        println("Voce é maior de idade!!")
    }else{
        println("voce e menor de Idade!!")
    }
    println("Continuando...")


    var cor = "azul"
    if (cor == "varmelho" ) {
        println("Voce escolheu Vermelho!")
    }else if(cor == "azul") {
        println("Voce escolheu AZUL!")
    } else {
        println("Voce escolheu amarelo!!")
    }

    var numero = 10
    when (numero % 2){
        0 -> println("O Numero $numero e par! ")
        else -> println("O Numero $numero e Impar!")
    }

    var letra = "Z"
    when(letra){
        "a", "e", "i", "o", "u" -> println("VOGAL")
        else -> println("Consoante")
    }
    println("FIM!!")

}