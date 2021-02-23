package com.epam.uber.data.parser;

import com.epam.uber.logic.Human;

import java.util.List;

public interface HumanParser {
    List<Human> parseHumans(String dataLines);
}
