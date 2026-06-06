use std::env;

fn hello() {
    println!("Hello, world!");
}

fn initialise(len: usize, tab1: &mut Vec<Vec<f64>>, tab2: &mut Vec<Vec<f64>>) {
    let mut pos = 0;

    for i in 0..len {
        for j in 0..len {
            pos += i + j;
            if pos % 2 == 0 {
                tab1[i][j] = 1.1;
            } else {
                tab1[i][j] = 1.0;
            }
            if (pos + 1) % 2 == 0 {
                tab2[i][j] = 1.1;
            } else {
                tab2[i][j] = 1.0;
            }
        }
    }
}

fn mult_tab(tab1: Vec<Vec<f64>>, tab2: Vec<Vec<f64>>, len: usize) -> Vec<Vec<f64>> {
    let mut res = vec![vec![0.0; len]; len];

    for i in 0..len {
        for j in 0..len {
            let mut m = 0.0;

            for n in 0..len {
                m += tab1[i][n] * tab2[n][j];
            }

            res[i][j] = m;
        }
    }

    return res;
}

fn mult(args_cp: Vec<String>) {
    let mut len: usize = 3;

    if args_cp.len() > 0 {
        let s = &args_cp[0];
        len = s.parse::<usize>().unwrap();
    }

    let mut tab1 = vec![vec![0.0; len]; len];
    let mut tab2 = vec![vec![0.0; len]; len];

    initialise(len, &mut tab1, &mut tab2);

    let _res = mult_tab(tab1, tab2, len);

    //println!("{:?}", _res);
}

fn main() {
    let args: Vec<String> = env::args().collect();
    let mut args_cp: Vec<String> = args.clone();

    args_cp.remove(0);

    let mut operateur = 2;

    if args_cp.len() > 0 {
        let op = &args_cp[0];
        if op == "helloword" {
            operateur = 1;
            args_cp.remove(0);
        } else if op == "mult" {
            operateur = 2;
            args_cp.remove(0);
        }
    }

    if operateur == 1 {
        hello();
    } else if operateur == 2 {
        mult(args_cp);
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_mult() {
        let len: usize = 3;

        let mut tab1 = vec![vec![0.0; len]; len];
        let mut tab2 = vec![vec![0.0; len]; len];

        initialise(len, &mut tab1, &mut tab2);

        let res = mult_tab(tab1, tab2, len);

        assert_eq!(len, res.len());
        assert_approx_eq(3.2, res[0][0], 0.001);
        assert_approx_eq(3.21, res[0][1], 0.001);
        assert_approx_eq(3.31, res[0][2], 0.001);
        assert_approx_eq(3.3, res[1][0], 0.001);
        assert_approx_eq(3.31, res[1][1], 0.001);
        assert_approx_eq(3.42, res[1][2], 0.001);
        assert_approx_eq(3.31, res[2][0], 0.001);
        assert_approx_eq(3.3, res[2][1], 0.001);
        assert_approx_eq(3.41, res[2][2], 0.001);
    }

    fn assert_approx_eq(a: f64, b: f64, epsilon: f64) {
        assert!(
            (a - b).abs() < epsilon,
            "{} n'est pas suffisamment proche de {}",
            a,
            b
        );
    }
}
