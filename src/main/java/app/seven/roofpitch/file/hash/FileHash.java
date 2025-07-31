package app.seven.roofpitch.file.hash;

import app.seven.roofpitch.PojaGenerated;

@PojaGenerated
public record FileHash(FileHashAlgorithm algorithm, String value) {}
