export class LocalStorageUtil {
    /**
     * Save data to localStorage
     * @param key The key under which the data will be saved
     * @param value The value to save (can be any serializable object)
     */
    static save<T>(key: string, value: T): void {
      try {
        const serializedValue = JSON.stringify(value);
        localStorage.setItem(key, serializedValue);
      } catch (error) {
        console.error(`Error saving data to localStorage with key: ${key}`, error);
      }
    }
  
    /**
     * Retrieve data from localStorage
     * @param key The key under which the data is stored
     * @returns The retrieved data, or `null` if the key does not exist or parsing fails
     */
    static get<T>(key: string): T | null {
      try {
        const serializedValue = localStorage.getItem(key);
        if (serializedValue === null) {
          return null;
        }
        return JSON.parse(serializedValue) as T;
      } catch (error) {
        console.error(`Error retrieving data from localStorage with key: ${key}`, error);
        return null;
      }
    }
  
    /**
     * Update data in localStorage by merging new data with the existing data
     * @param key The key under which the data is stored
     * @param value The value to merge with the existing data
     */
    static update<T>(key: string, value: Partial<T>): void {
      try {
        const existingValue = this.get<T>(key);
        if (existingValue) {
          const updatedValue = { ...existingValue, ...value };
          this.save(key, updatedValue);
        } else {
          console.warn(`Key "${key}" not found in localStorage. Saving new data.`);
          this.save(key, value);
        }
      } catch (error) {
        console.error(`Error updating data in localStorage with key: ${key}`, error);
      }
    }
  
    /**
     * Remove data from localStorage
     * @param key The key under which the data is stored
     */
    static remove(key: string): void {
      try {
        localStorage.removeItem(key);
      } catch (error) {
        console.error(`Error removing data from localStorage with key: ${key}`, error);
      }
    }
  
    /**
     * Clear all data from localStorage
     */
    static clear(): void {
      try {
        localStorage.clear();
      } catch (error) {
        console.error('Error clearing localStorage', error);
      }
    }
  }
  