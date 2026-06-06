use std::env;

fn hello() {
    println!("Hello, world!");
}

fn mult(args_cp: Vec<String>) {
    let mut len: usize = 3;

    if args_cp.len() > 0 {
        let s = &args_cp[0];
        len = s.parse::<usize>().unwrap();
    }

    let mut tab1 = vec![vec![0.0; len]; len];
    let mut tab2 = vec![vec![0.0; len]; len];
    let mut res = vec![vec![0.0; len]; len];

    let mut pos = 0;

    for i in 0..len - 1 {
        for j in 0..len - 1 {
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

    for i in 0..len - 1 {
        for j in 0..len - 1 {
            let mut m = 0.0;

            for n in 0..len - 1 {
                m += tab1[i][n] * tab2[n][j];
            }

            res[i][j] = m;
        }
    }
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
