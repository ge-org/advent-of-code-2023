use std::fs::read_to_string;

pub fn day001() {
    assert_eq!(142, day001_part1("inputs/001_1_sample.txt"));
    assert_eq!(55002, day001_part1("inputs/001_puzzle.txt"));

    assert_eq!(281, day001_part2("inputs/001_2_sample.txt"));
    println!("{}", day001_part2("inputs/001_puzzle.txt"));
}

fn day001_part1(path: &str) -> u32 {
    let lines = read_lines(path);
    let sum_per_line: Vec<u32> = lines.iter().map(|l| get_sum(l)).collect();
    sum_per_line.iter().sum()
}

fn day001_part2(path: &str) -> u32 {
    let lines = read_lines(path);
    let sum_per_line: Vec<u32> = lines
        .iter()
        .map(|l| get_sum(&substitute_literal_digits(l)))
        .collect();
    sum_per_line.iter().sum()
}

fn substitute_literal_digits(line: &str) -> String {
    let mut f: Vec<(&&str, Option<usize>, &&str)> = LITERAL_DIGITS
        .iter()
        .map(|(key, value)| {
            let p = line.find(key);
            (key, p, value)
        })
        .filter(|a| a.1.is_some())
        .collect();
    f.sort_by_key(|k| k.1);

    let mut replacements: Vec<String> = Vec::new();
    for (k, _, v) in f {
        replacements.push(line.replace(k, v));
    }
    if replacements.is_empty() {
        line.to_string()
    } else {
        replacements.join("")
    }
}

fn get_sum(line: &str) -> u32 {
    let digits: Vec<_> = line.chars().filter(|a| a.is_ascii_digit()).collect();
    let mut concatenated_digits = String::from("");
    concatenated_digits.push(*digits.first().unwrap());
    concatenated_digits.push(*digits.last().unwrap());
    concatenated_digits.parse::<u32>().unwrap()
}

static LITERAL_DIGITS: [(&str, &str); 9] = [
    ("one", "1"),
    ("two", "2"),
    ("three", "3"),
    ("four", "4"),
    ("five", "5"),
    ("six", "6"),
    ("seven", "7"),
    ("eight", "8"),
    ("nine", "9"),
];

fn read_lines(filename: &str) -> Vec<String> {
    read_to_string(filename)
        .unwrap()
        .lines()
        .map(String::from)
        .collect()
}
