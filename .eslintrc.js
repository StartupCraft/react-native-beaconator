module.exports = {
  root: true,
  parser: '@typescript-eslint/parser',
  parserOptions: {
    sourceType: 'module',
    project: './tsconfig.json',
    ecmaVersion: 2018,
    ecmaFeatures: {
      jsx: true,
    },
  },
  ignorePatterns: ['scripts', 'lib', 'docs', 'example'],
  plugins: ['@typescript-eslint', 'prettier'],
  extends: ['airbnb', 'airbnb/hooks', 'prettier', 'plugin:@typescript-eslint/recommended'],
  rules: {
    // -- Prettier
    'prettier/prettier': 'error',
    // -- TypeScript
    '@typescript-eslint/camelcase': 'off',
    '@typescript-eslint/explicit-function-return-type': 'off',
    '@typescript-eslint/explicit-member-accessibility': 'off',
    '@typescript-eslint/explicit-module-boundary-types': 'off',
    '@typescript-eslint/no-empty-function': 'off',
    '@typescript-eslint/no-explicit-any': 'off',
    '@typescript-eslint/no-extra-semi': 'off',
    '@typescript-eslint/no-namespace': 'off',
    '@typescript-eslint/no-shadow': 'error',
    '@typescript-eslint/no-unused-vars': 'warn',
    // -- React
    'react-hooks/exhaustive-deps': 'warn',
    // -- JSX A11y
    'jsx-a11y/anchor-is-valid': 'off',
    // -- Import
    'import/extensions': [
      'error',
      'ignorePackages',
      {
        js: 'never',
        ts: 'never',
        jsx: 'never',
        tsx: 'never',
      },
    ],
    'import/prefer-default-export': 'off',
    // -- ESLint
    'no-bitwise': 'off',
    'no-plusplus': 'off',
    'no-shadow': 'off',
    'no-use-before-define': 'off',
    'lines-between-class-members': 'off',
  },
  env: {
    node: true,
  },
};
