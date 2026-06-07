#!/usr/bin/env node
import { program } from 'commander';

program
  .option('-n, --name <nom>', 'Ton prénom')
  .parse();

const { name } = program.opts();
console.log(`Bonjour, ${name ?? 'inconnu'} !`);
