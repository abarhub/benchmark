package main

import (
	"math"
	"testing"
)

func Test_multi_calcul(t *testing.T) {
	tests := []struct {
		name string // description of this test case
		// Named input parameters for target function.
		tab1 [][]float64
		tab2 [][]float64
		lenx int
		want [][]float64
	}{
		{name: "test1",
			tab1: [][]float64{{1.1, 1.0, 1.1}, {1.0, 1.1, 1.0}, {1.1, 1.0, 1.1}},
			tab2: [][]float64{{1.0, 1.1, 1.0}, {1.1, 1.0, 1.1}, {1.0, 1.1, 1.0}},
			lenx: 3,
			want: [][]float64{{3.3, 3.42, 3.3}, {3.21, 3.3, 3.21}, {3.3, 3.42, 3.3}},
		},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			got := multi_calcul(tt.tab1, tt.tab2, tt.lenx)
			// TODO: update the condition below to compare got with tt.want.
			if !compare(got, tt.want, t) {
				t.Errorf("multi_calcul() = %v, want %v", got, tt.want)
			}
		})
	}
}

func compare(got, want [][]float64, t *testing.T) bool {

	for i := range len(want) {
		for j := range len(want[i]) {
			if !valeursEgales(want[i][j], got[i][j]) {
				t.Errorf("les valeurs [%d][%d] sont différentes %f!=%f", i, j, want[i][j], got[i][j])
				return false
			}
		}
	}
	return true
}

func valeursEgales(val, val2 float64) bool {
	return math.Abs(val-val2) < 0.001
}
