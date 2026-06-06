fn hello() {
    println!("Hello, world!");
}

fn mult() {
    const LEN: usize = 3;

    let mut tab1: [[f64; LEN]; LEN] = [[0.0; LEN]; LEN];
    let mut tab2: [[f64; LEN]; LEN] = [[0.0; LEN]; LEN];
    let mut res: [[f64; LEN]; LEN] = [[0.0; LEN]; LEN];

    let mut pos = 0;

    for i in 0..LEN - 1 {
        for j in 0..LEN - 1 {
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

    for i in 0..LEN - 1 {
        for j in 0..LEN - 1 {
            let mut m = 0.0;

            for n in 0..LEN - 1 {
                m += tab1[i][n] * tab2[n][j];
            }

            res[i][j] = m;
        }
    }
}

fn main() {
    if false {
        hello();
    } else if true {
        mult();
    }
}
