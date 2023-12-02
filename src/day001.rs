use std::fs::read_to_string;

pub fn day001() {
    let lines = read_lines("inputs/001_sample.txt");
    for line in lines {
        println!("{}", line);
    }
}

fn read_lines(filename: &str) -> Vec<String> {
    read_to_string(filename)
        .unwrap()
        .lines()
        .map(String::from)
        .collect()
}
