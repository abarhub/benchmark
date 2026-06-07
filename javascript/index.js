#!/usr/bin/env node
import { program } from 'commander';

function hello(program) {
    const { name } = program.opts();
    console.log(`Bonjour, ${name ?? 'inconnu'} !`);
}

function initialisation(len, decalage) {

    let res = [];
    for (let i = 0; i < len; i++) {
        let tab = [];
        res.push(tab);
        for (let j = 0; j < len; j++) {

            let m = 0.0;
            let pos = i + j;
            if (decalage) {
                pos += 1;
            }

            if (pos % 2 == 0) {
                m = 1.1;
            } else {
                m = 1.0;
            }
            tab.push(m);

        }
    }
    return res;

}

function multi_calcul(tab1, tab2, len) {
    let res = [];
    for (let i = 0; i < len; i++) {
        let tab = [];
        res.push(tab);
        for (let j = 0; j < len; j++) {

            let m = 0.0;
            for (let k = 0; k < len; k++) {
                m += tab1[i][k] * tab2[k][j];
            }
            tab.push(m);

        }

    }

    return res;
}

function multi(program) {

    let len = 3;
    let debug = false;

    const options = program.opts();
    if (options.debug) {
        debug = true;
    }

    let tailleParam = program.args[0]
    if (tailleParam && tailleParam > 0) {
        len = tailleParam;
    }

    if (debug) {
        console.log(`len= ${len}`);
    }

    let tab1 = initialisation(len, false);
    let tab2 = initialisation(len, true);

    let res = multi_calcul(tab1, tab2, len);

    if (debug) {
        console.log(`res= ${res}`);
    }
}

function main() {

    program
        .option('-n, --name <nom>', 'Ton prénom')
        .option('-o, --operation <operation>', "L'opération")
        .option('-d, --debug', "debug")
        .argument('[taille]', 'la taille', 'la taille')
        .parse();

    let oper = 2;

    const { operation } = program.opts();

    if (operation == "helloworld") {
        oper = 1;
    } else if (operation == "multi") {
        oper = 2;
    }

    if (oper == 1) {
        hello(program);
    } else if (oper == 2) {
        multi(program);
    }

}

main();

