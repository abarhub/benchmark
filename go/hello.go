package main

import (
	"fmt"
	"os"
	"strconv"
)

func hello() {
	fmt.Println("Hello, World!")
}

func initialisation(lenx int, decalage bool) [][]float64 {
	tab := make([][]float64, lenx)
	for i := 0; i < len(tab); i++ {

		tab[i] = make([]float64, lenx)
		for j := 0; j < len(tab[i]); j++ {

			pos := i + j
			if decalage {
				pos++
			}

			if pos%2 == 0 {
				tab[i][j] = 1.1
			} else {
				tab[i][j] = 1.0
			}

		}
	}
	return tab
}

func multi_calcul(tab1 [][]float64, tab2 [][]float64, lenx int) [][]float64 {
	res := make([][]float64, lenx)
	for i := 0; i < lenx; i++ {
		res[i] = make([]float64, lenx)
		for j := 0; j < lenx; j++ {

			m := 0.0
			for k := 0; k < lenx; k++ {
				m += tab1[i][k] * tab2[k][j]
			}
			res[i][j] = m

		}

	}

	return res
}

func multi(args []string) {
	lenx := 3
	debug := false

	if len(args) > 0 {
		for i := 0; i < len(args); i++ {
			s := args[i]
			if s == "--debug" {
				debug = true
			} else {
				n, err := strconv.Atoi(s)
				if err != nil {
					fmt.Println("Can't convert this to an int!")
				} else {
					lenx = n
				}
			}
		}
	}

	if debug {
		fmt.Println("len=", lenx)
	}

	tab1 := initialisation(lenx, false)
	tab2 := initialisation(lenx, true)

	res := multi_calcul(tab1, tab2, lenx)

	if debug {
		fmt.Println("res=", res)
	}
}

func main() {
	operateur := 2

	argsWithoutProg := os.Args[1:]

	if operateur == 1 {
		hello()
	} else if operateur == 2 {
		multi(argsWithoutProg)
	}
}
