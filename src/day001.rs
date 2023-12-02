use std::fs::read_to_string;

pub fn day001() {
    assert_eq!(142, day001_part1("inputs/001_sample.txt"));
    println!("{}", day001_part1("inputs/001_puzzle.txt"));
}

fn day001_part1(path: &str) -> u32 {
    let lines = read_lines(path);
    let sum_per_line: Vec<u32> = lines.iter().map(|a| get_sum(a)).collect();
    sum_per_line.iter().sum()
}

fn day001_part2(path: &str) -> u32 { 42 }

fn get_sum(line: &str) -> u32 {
    let digits: Vec<_> = line.chars().filter(|a| a.is_ascii_digit()).collect();
    let mut concatenated_digits = String::from("");
    concatenated_digits.push(*digits.first().unwrap());
    concatenated_digits.push(*digits.last().unwrap());
    concatenated_digits.parse::<u32>().unwrap()
}


fn read_lines(filename: &str) -> Vec<String> {
    read_to_string(filename)
        .unwrap()
        .lines()
        .map(String::from)
        .collect()
}
